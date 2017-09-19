package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaCourseMediaDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseMedia;
import cms.academic.academicapp.service.CourseMediaService;


@Transactional(propagation = Propagation.REQUIRED)
public class CourseMediaServiceImpl implements CourseMediaService {

	@Autowired
    private JpaCourseMediaDao courseMediaDao = null;
	
	public JpaCourseMediaDao getCourseMediaDao() {
		return courseMediaDao;
	}

	public void setCourseMediaDao(JpaCourseMediaDao courseMediaDao) {
		this.courseMediaDao = courseMediaDao;
	}

	@Override
	public Integer addCourseMedia(CourseMedia media) {
		try {
			if(media != null){
				courseMediaDao.create(media);
				return 1;
			}		
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}


	@Override
	public void removeCourseMedia(CourseMedia media) {
		try {
			if(media != null){
				courseMediaDao.deleteById(media.getId());				
			}		
		} catch (Exception e) {			
		}		
	}

	@Override
	public List<CourseMedia> courseMediaList(Course course) {
		try {
			List<CourseMedia> all = courseMediaDao.getAll();
			List<CourseMedia> media = new ArrayList<CourseMedia>();
			
			for(CourseMedia c : all){
				if(c.getCourse().equals(course)){
					media.add(c);
				}
			}
			if(media != null){
				return media;
			}
		} catch (Exception e) {
			return null;
		}
		return null; 
	}

	@Override
	public CourseMedia findMediaById(Long id) {
		try {
			return courseMediaDao.get(id);	
		} catch (Exception e) {
			return null;
		}
	}
	
}
