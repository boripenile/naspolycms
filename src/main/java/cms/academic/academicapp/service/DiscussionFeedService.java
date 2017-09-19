package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.DiscussionFeed;
import cms.academic.academicapp.model.Mail;


public interface DiscussionFeedService {
    public void addDiscussionFeed(DiscussionFeed feed);
    
    public Discussion findDiscussion(Long discussionId);
    
    public List<DiscussionFeed> viewDiscussionFeeds(Discussion discussion);
    
    public void sendMailToMemberBySystem(Mail mail);
    
}
