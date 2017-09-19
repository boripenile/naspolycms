package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;

public interface MemberCourseService {
	public Integer registerCourse(List<RegisteredCourse> memberCourses);
    
    public List<User> viewCourseFacultyMembers(Course course);
    
    public List<RegisteredCourse> registeredCourses(User member) ;
    
    public List<RegisteredCourse> allRegisteredCourses() ;
    
    public Integer totalRegisteredCourse(User member);
    
    public Integer removeRegisteredCourse(RegisteredCourse course);
    
    public RegisteredCourse viewRegisteredCourse(Long course) ;
}
