package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaDiscussionDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.MemberDiscussionService;


@Transactional(propagation = Propagation.REQUIRED)
public class MemberDiscussionServiceImpl implements MemberDiscussionService {

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
	public List<Discussion> courseDiscussions(
			RegisteredCourse registeredCourse, User member) {
		try {
			List<Discussion> all = discussionDao.getAll();
			List<Discussion> list = new ArrayList<Discussion>();
			
			if(all != null){
				for(Discussion c : all){
					if(c.getCourse().equals(registeredCourse.getCourse())
							&& c.getMember().getEmailaddress().equals(member.getEmailaddress())){
						list.add(c);
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer removeDiscussion(Discussion discussion) {
		try {
			if(discussion != null){
				discussionDao.deleteById(discussion.getId());
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public List<Discussion> viewRegisteredCourseDiscussions(
			RegisteredCourse registeredCourse) {
		try {
			List<Discussion> all = discussionDao.getAll();
			List<Discussion> list = new ArrayList<Discussion>();
			
			if(all != null){
				for(Discussion c : all){
					if(c.getCourse().equals(registeredCourse.getCourse())
							&& c.getOwner().equals(registeredCourse.getMember())){
						list.add(c);
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Discussion> viewDiscussions(Course course) {
		try {
			List<Discussion> all = discussionDao.getAll();
			List<Discussion> list = new ArrayList<Discussion>();
			
			if(all != null){
				for(Discussion c : all){
					if(c.getCourse().equals(course)){
						list.add(c);
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void discontinueDiscussion(Discussion discussion,
			Boolean isDiscontinued) {
		try {
			Discussion dis = discussionDao.get(discussion.getId());
			if(dis != null){
				dis.setDiscontinue(isDiscontinued);
				discussionDao.update(dis);
			}
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public Discussion viewDiscussion(Long discussionId) {
		try {
			return discussionDao.get(discussionId);	
		} catch (Exception e) {
			return null;
		}
	}
}
