package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseMedia;

public interface CourseMediaService {
    public Integer addCourseMedia(CourseMedia media);    
    
    public void removeCourseMedia(CourseMedia media);
    
    public CourseMedia findMediaById(Long id);
    
    public List<CourseMedia> courseMediaList(Course course);    
    
}
