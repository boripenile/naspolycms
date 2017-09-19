package cms.academic.academicapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaCourseDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.service.CourseService;

@Transactional(propagation = Propagation.REQUIRED)
public class CourseServiceImpl implements CourseService {
    @Autowired
    private JpaCourseDao courseDao = null;
    
    public Boolean isCourseExits(String code) {
		try {
			Course course = courseDao.findByCode(code);
			if(course != null){
				return true;
			}
		} catch (Exception e) {
			return false;
		}		
		return false;   
    }
	@Override
	public Boolean registerCourse(Course newCourse) throws Exception{
			if(!isCourseExits(newCourse.getCourseCode())){
				courseDao.create(newCourse);
				return true;
			}
		    return false;
	}

	@Override
	public List<Course> courses() {
		try {
			return courseDao.getAll();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Course findByCourseCode(String code) {
		try {
			return courseDao.findByCode(code);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Course findById(Long Id) {
		try {
			return courseDao.get(Id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void update(Course course) throws Exception{
		courseDao.update(course);
	}

}
