package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.User;

public interface StudentCourseService {
	public Integer enrolForCourse(List<EnrolledCourse> studentCourses);
	
	public List<EnrolledCourse> enrolledCourses(User student);
	
	public Integer totalEnrolledCourse(User student);    
	
	public List<User> viewEnrolledStudentsPerCourse(Course course); 
	
	public Integer removeEnrolledCourse(EnrolledCourse course);   
	
    public EnrolledCourse viewEnrolledCourse(Long id);
}
