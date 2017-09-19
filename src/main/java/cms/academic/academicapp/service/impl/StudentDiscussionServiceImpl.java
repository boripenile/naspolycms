package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaDiscussionDao;
import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.StudentDiscussionService;


@Transactional(propagation = Propagation.REQUIRED)
public class StudentDiscussionServiceImpl implements StudentDiscussionService {
    @Autowired
    private JpaDiscussionDao discussionDao = null;
    
	@Override
	public Integer addNewDiscussion(Discussion discussion) {
		try {
			if(discussion != null){
				discussionDao.create(discussion);
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}
    
	@Override
	public Discussion viewDiscussion(Long discussionId){
		try {
			return discussionDao.get(discussionId);	
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Integer editDiscussion(Discussion discussion) {
		try {
			if(discussion != null){
				discussionDao.update(discussion);
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public List<Discussion> courseDiscussions(EnrolledCourse enrolledCourse) {
		try {
			List<Discussion> list = discussionDao.getAll();
			List<Discussion> discussions = new ArrayList<Discussion>();
			if(list != null){
				for(Discussion r : list){
					if(r.getCourse().equals(enrolledCourse.getCourse())){
						discussions.add(r);
					}
				}
			}
			return discussions;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer removeDiscussion(Discussion discussion, User owner) {
		try {
			if(owner != null){
				Discussion dis = viewDiscussion(discussion.getId());
				if(dis != null && dis.getOwner().getEmailaddress().equals(owner.getEmailaddress())){
					discussionDao.deleteById(discussion.getId());
					return 1;
				}		
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}
}
