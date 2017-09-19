package cms.academic.academicapp.dao;

import cms.academic.academicapp.model.AnsweredCourseAssignment;

public interface AnsweredCourseAssignmentDao extends Dao<AnsweredCourseAssignment> {
	void saveAssignment(AnsweredCourseAssignment assignment);
}
