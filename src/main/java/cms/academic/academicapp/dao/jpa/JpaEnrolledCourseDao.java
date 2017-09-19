package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.EnrolledCourseDao;
import cms.academic.academicapp.model.EnrolledCourse;

@Repository
public class JpaEnrolledCourseDao extends JpaAbstractDao<EnrolledCourse> implements EnrolledCourseDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
