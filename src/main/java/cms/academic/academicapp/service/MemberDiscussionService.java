package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;


public interface MemberDiscussionService {
    public Integer addNewDiscussion(Discussion discussion);
    
    public Integer editDiscussion(Discussion discussion);
    
    public List<Discussion> courseDiscussions(RegisteredCourse registeredCourse, User member);
    
    public Integer removeDiscussion(Discussion discussion);
    
    public List<Discussion> viewRegisteredCourseDiscussions(RegisteredCourse registeredCourse);
    
    public List<Discussion> viewDiscussions(Course course);
    
    public void discontinueDiscussion(Discussion discussion, Boolean isDiscontinued);
    
    public Discussion viewDiscussion(Long discussionId);

}
