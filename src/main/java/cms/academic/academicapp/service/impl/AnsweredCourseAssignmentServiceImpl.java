package cms.academic.academicapp.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.AnsweredCourseAssignmentDao;
import cms.academic.academicapp.model.AnsweredCourseAssignment;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.AnsweredCourseAssignmentService;

@Transactional
public class AnsweredCourseAssignmentServiceImpl implements AnsweredCourseAssignmentService  {

	@Autowired
	private AnsweredCourseAssignmentDao answeredAssignmentDao  = null;	
	
	public AnsweredCourseAssignmentDao getAnsweredAssignmentDao() {
		return answeredAssignmentDao;
	}

	public void setAnsweredAssignmentDao(
			AnsweredCourseAssignmentDao answeredAssignmentDao) {
		this.answeredAssignmentDao = answeredAssignmentDao;
	}

	@Override
	public List<AnsweredCourseAssignment> getAllAnswers(){
		return answeredAssignmentDao.getAll();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer submitCourseAssignmentByStudent(
			AnsweredCourseAssignment assignment) {
		if(assignment != null){
			if(assignment.getAnswer() == ""){
				return -1;
			}
			else{
				try {
					List<AnsweredCourseAssignment> all = answeredAssignmentDao.getAll();
					if(all != null){
						for (AnsweredCourseAssignment a : all){
							if(a.getStudent().equals(assignment.getStudent()) && 
									a.getAssignment().getQuestion().equals(assignment.getAssignment().getQuestion())){
								return -1;
							}
						}
					}
					answeredAssignmentDao.create(assignment);
					return 1;
				} catch (Exception e) {
					return -1;
				}
			}
		}
		return -1;
		
	}

	@Override
	public AnsweredCourseAssignment viewAnsweredCourseAssignmentByStudent(
			Long id) {
		try {
			return answeredAssignmentDao.get(id);
		} catch (Exception e) {
			return null;
		} 
	}

	@Override
	public Integer removeAnsweredCourseAssignmentByStudent(
			AnsweredCourseAssignment assignment) {
		try{
			answeredAssignmentDao.deleteById(assignment.getId());
			return 1;
		}catch(Exception e){
			return -1;
			
		}
	}

	@Override
	public List<AnsweredCourseAssignment> viewAnweredCourseAssignmentsByStudent(
			User student, EnrolledCourse course) {
		List<AnsweredCourseAssignment> answered = new ArrayList<AnsweredCourseAssignment>();
		try {
			List<AnsweredCourseAssignment> list  = new ArrayList<AnsweredCourseAssignment>();
			 
			list = answeredAssignmentDao.getAll();
			if(list != null){
				for (AnsweredCourseAssignment a: list){
					if(a.getAssignment().getCourse().equals(course.getCourse()) &&
							a.getStudent().equals(student)){
						answered.add(a);
					}
				}
			}
			
		} catch (Exception e) {
			return null;
		}
		return (answered != null ? answered : null);
	}

	@Override
	public Integer gradeAnweredCourseAssignmentByMember(
			AnsweredCourseAssignment assignment) {
		if(assignment != null){
			if(assignment.getAnswer() == ""){
				return -1;
			}
			else{
				try {
					answeredAssignmentDao.update(assignment);
					return 1;
				} catch (Exception e) {
					return -1;
				}
			}
		}
		return -1;
	}

	
	@Override
	public Integer editAnsweredCourseAssignmentByStudent(
			AnsweredCourseAssignment assignment) {
		if(assignment != null){
			if(assignment.getAnswer() == ""){
				return -1;
			}
			else{
				try {
					answeredAssignmentDao.update(assignment);
					return 1;
				} catch (Exception e) {
					return -1;
				}
			}
		}
		return -1;
	}

	
	@Override
	public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByStudent(
			Course course, User student) {
		List<AnsweredCourseAssignment> answered = new ArrayList<AnsweredCourseAssignment>();
		try {
			List<AnsweredCourseAssignment> list  = new ArrayList<AnsweredCourseAssignment>();
			 
			list = answeredAssignmentDao.getAll();
			if(list != null){
				for (AnsweredCourseAssignment a: list){
					if(a.getAssignment().getCourse().equals(course)
							&& a.getStudent().equals(student)){
						answered.add(a);
					}
				}
			}			
			
		} catch (Exception e) {
			return null;
		}		
		return (answered != null ? answered : null);
	}

	@Override
	public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByMember(
			CourseAssignment assignment, User member) {
		List<AnsweredCourseAssignment> answered = new ArrayList<AnsweredCourseAssignment>();
		try {
			List<AnsweredCourseAssignment> list  = new ArrayList<AnsweredCourseAssignment>();			 
			list = answeredAssignmentDao.getAll();
			if(list != null){
				for (AnsweredCourseAssignment a: list){
					if(a.getAssignment().equals(assignment)
							&& a.getAssignment().getMember().equals(member)){
						answered.add(a);
					}
				}
			}
			
		} catch (Exception e) {
			return null;
		}		
		return (answered != null ? answered : null);
	}

	@Override
	public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentsByStudent(
			User student) {
		List<AnsweredCourseAssignment> answered = new ArrayList<AnsweredCourseAssignment>();
		try {
			List<AnsweredCourseAssignment> list  = new ArrayList<AnsweredCourseAssignment>();			 
			list = answeredAssignmentDao.getAll();
			if(list != null){
				for (AnsweredCourseAssignment a: list){
					if(a.getStudent().equals(student)){
						answered.add(a);
					}
				}
			}
				
		} catch (Exception e) {
			return null;
		}	
		return (answered != null ? answered : null);
	}

	@Override
	public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByMember(
			Course course, User member) {
		List<AnsweredCourseAssignment> answered = new ArrayList<AnsweredCourseAssignment>();
		try {
			List<AnsweredCourseAssignment> list  = new ArrayList<AnsweredCourseAssignment>();			 
			list = answeredAssignmentDao.getAll();
			if(list != null){
				for (AnsweredCourseAssignment a: list){
					if(a.getAssignment().getCourse().equals(course)
							&& a.getAssignment().getMember().equals(member)){
						answered.add(a);
					}
				}
			}
			
		} catch (Exception e) {
			return null;
		}		
		return (answered != null ? answered : null);
	}

	@Override
	public int saveAssignment(AnsweredCourseAssignment assignment) {
		try {
			answeredAssignmentDao.saveAssignment(assignment);
			return 1;
		} catch (Exception e) {
			return -1;
		}		
	}

}
