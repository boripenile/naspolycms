package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;

public interface CourseService {
    public Boolean registerCourse(Course newCourse) throws Exception;
    
    public List<Course> courses();
    
    public Course findByCourseCode(String code);
    
    public Course findById(Long Id);
    
    public void update(Course course) throws Exception;
   
}
