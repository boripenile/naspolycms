package cms.academic.academicapp.dao;

import cms.academic.academicapp.model.Course;


public interface CourseDao extends Dao<Course>{
	public Course findByCode(String code);
}
