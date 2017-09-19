package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaCourseAssignmentDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;
import cms.academic.academicapp.service.MemberCourseAssignmentService;


@Transactional(propagation = Propagation.REQUIRED)
public class MemberCourseAssignmentServiceImpl implements MemberCourseAssignmentService {

	@Autowired
    private JpaCourseAssignmentDao courseAssignmentDao = null;
	
	@Override
	public Integer addCourseAssignmennt(CourseAssignment assignment) {
		try {
			if(assignment != null){
				courseAssignmentDao.create(assignment);
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public List<CourseAssignment> viewCourseAssignments(
			Course registeredCourse) {
		try {
			List<CourseAssignment> all = courseAssignmentDao.getAll();
			List<CourseAssignment> list = new ArrayList<CourseAssignment>();
			
			if(all != null){
				for(CourseAssignment c : all){
					if(c.getCourse().equals(registeredCourse)){
						list.add(c);
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CourseAssignment viewCourseAssignment(Long assignmentId) {
		try {
			return courseAssignmentDao.get(assignmentId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer editCourseAssignment(CourseAssignment assignment) {
		try {
			if(assignment != null){
				courseAssignmentDao.update(assignment);
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public Integer removeCourseAssignment(CourseAssignment assignment) {
		try {
			if(assignment != null){
				courseAssignmentDao.deleteById(assignment.getId());
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public List<CourseAssignment> viewAssignmentsByCourse(Course course) {
		try {
			List<CourseAssignment> all = courseAssignmentDao.getAll();
			List<CourseAssignment> list = new ArrayList<CourseAssignment>();
			
			if(all != null){
				for(CourseAssignment c : all){
					if(c.getCourse().equals(course)){
						list.add(c);
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
