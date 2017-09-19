package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaDiscussionDao;
import cms.academic.academicapp.dao.jpa.JpaDiscussionFeedDao;
import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.DiscussionFeed;
import cms.academic.academicapp.model.Mail;
import cms.academic.academicapp.service.DiscussionFeedService;

@Transactional(propagation = Propagation.REQUIRED)
public class DiscussionFeedServiceImpl implements DiscussionFeedService {

	@Autowired
	private JpaDiscussionFeedDao discussionFeedDao = null;

	@Autowired
	private JpaDiscussionDao discussionDao = null;
	
	@Override
	public void addDiscussionFeed(DiscussionFeed feed) {
		try {
			discussionFeedDao.create(feed);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public List<DiscussionFeed> viewDiscussionFeeds(Discussion discussion) {
		try {
			List<DiscussionFeed> all = discussionFeedDao.getAll();
			List<DiscussionFeed> list = new ArrayList<DiscussionFeed>();
			if (all != null) {
				for (DiscussionFeed f : all) {
					if (f.getDiscussion().equals(discussion)) {
						list.add(f);
					}
				}			
			}
			return list;			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void sendMailToMemberBySystem(Mail mail) {

	}

	@Override
	public Discussion findDiscussion(Long discussionId) {
		return discussionDao.get(discussionId);
	}
}
