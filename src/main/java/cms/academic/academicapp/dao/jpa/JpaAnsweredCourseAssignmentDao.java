package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.AnsweredCourseAssignmentDao;
import cms.academic.academicapp.model.AnsweredCourseAssignment;

@Repository
public class JpaAnsweredCourseAssignmentDao extends JpaAbstractDao<AnsweredCourseAssignment> 
	implements AnsweredCourseAssignmentDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public void saveAssignment(AnsweredCourseAssignment assignment) {		
		entityManager.persist(assignment);
	}
}
