package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.AnsweredCourseAssignment;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.User;

public interface AnsweredCourseAssignmentService {
    public Integer submitCourseAssignmentByStudent(AnsweredCourseAssignment assignment);
    
    public int saveAssignment(AnsweredCourseAssignment assignment);
    
    public AnsweredCourseAssignment viewAnsweredCourseAssignmentByStudent(Long id);
    
    public Integer removeAnsweredCourseAssignmentByStudent(AnsweredCourseAssignment assignment) ;
    
    public List<AnsweredCourseAssignment> viewAnweredCourseAssignmentsByStudent(User student, EnrolledCourse course);
    
    public Integer gradeAnweredCourseAssignmentByMember(AnsweredCourseAssignment assignment) ;
    
    public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByMember(CourseAssignment assignment, User member);
    
    public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByMember(Course course, User member);

    public Integer editAnsweredCourseAssignmentByStudent(AnsweredCourseAssignment assignment);    
    
    public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentByStudent(Course course, User student) ;
    
    public List<AnsweredCourseAssignment> viewAnsweredCourseAssignmentsByStudent(User student) ;

	List<AnsweredCourseAssignment> getAllAnswers();
}
