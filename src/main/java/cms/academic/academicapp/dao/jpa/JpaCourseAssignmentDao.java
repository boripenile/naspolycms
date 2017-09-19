package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.CourseAssignmentDao;
import cms.academic.academicapp.model.CourseAssignment;

@Repository
public class JpaCourseAssignmentDao extends JpaAbstractDao<CourseAssignment>
	implements CourseAssignmentDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
