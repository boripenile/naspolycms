package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaCourseDao;
import cms.academic.academicapp.dao.jpa.JpaEnrolledCourseDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.StudentCourseService;

@Transactional(propagation = Propagation.REQUIRED)
public class StudentCourseServiceImpl implements StudentCourseService{

	@Autowired
    private JpaEnrolledCourseDao enrolledCourseDao = null;
	
	@Autowired
	private JpaCourseDao courseDao = null;
	
	public Boolean isCourseEnrolled(Course course) {
		try {
			List<Course> courses = courseDao.getAll();
			if(courses != null){
				for(Course c : courses){
					if(c.getCourseCode().equalsIgnoreCase(course.getCourseCode())){
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;    
    }
	@Override
	public Integer enrolForCourse(List<EnrolledCourse> studentCourses) {
		try {
			int result = 0;
			if(studentCourses != null){
				for(EnrolledCourse c : studentCourses){
					if(!isCourseEnrolledByStudent(c.getCourse(), c.getStudent())){
						enrolledCourseDao.create(c);
						result++;
					}				
				}
			}
			return result;
		} catch (Exception e) {
			
		}
		return 0;
	}
    
	private Boolean isCourseEnrolledByStudent(Course studentCourse, User student){
		List<EnrolledCourse> encourses = enrolledCourses(student);
		
		for(EnrolledCourse course: encourses){
			if(course.getCourse().equals(studentCourse) &&
					course.getStudent().getEmailaddress().equals(student.getEmailaddress())){
				return true;
			}				
		}		
		return false;
	}
	@Override
	public List<EnrolledCourse> enrolledCourses(User student) {
		try {
			List<EnrolledCourse> list = enrolledCourseDao.getAll();
			List<EnrolledCourse> enrolled = new ArrayList<EnrolledCourse>();
			if(list != null){
				for(EnrolledCourse r : list){
					if(r.getStudent().equals(student)){
						enrolled.add(r);
					}
				}
			}
			return enrolled;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer totalEnrolledCourse(User student) {
		try {
			int result = 0;
			List<EnrolledCourse> list = enrolledCourseDao.getAll();
			if(list != null){
				for(EnrolledCourse r : list){
					if(r.getStudent().equals(student)){
						result++;
					}
				}
			}
			return result;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<User> viewEnrolledStudentsPerCourse(Course course) {
		try {
			List<EnrolledCourse> list = enrolledCourseDao.getAll();
			List<User> students = new ArrayList<User>();
			if(list != null){
				for(EnrolledCourse r : list){
					if(r.getCourse().equals(course)){
						students.add(r.getStudent());
					}
				}
			}
			return students;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer removeEnrolledCourse(EnrolledCourse course) {
		try {
			if(course != null){
				enrolledCourseDao.deleteById(course.getId());
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public EnrolledCourse viewEnrolledCourse(Long id) {
		try {
			if(id != null){
				return enrolledCourseDao.get(id);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
