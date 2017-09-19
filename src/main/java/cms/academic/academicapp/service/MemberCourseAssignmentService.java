package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;

public interface MemberCourseAssignmentService {
    public Integer addCourseAssignmennt(CourseAssignment assignment) ;
    
    public List<CourseAssignment> viewCourseAssignments(Course registeredCourse);
    
    public CourseAssignment viewCourseAssignment(Long assignmentId) ;
    
    public Integer editCourseAssignment(CourseAssignment assignment);
    
    public Integer removeCourseAssignment(CourseAssignment assignment);
    
    public List<CourseAssignment> viewAssignmentsByCourse(Course course);
}
