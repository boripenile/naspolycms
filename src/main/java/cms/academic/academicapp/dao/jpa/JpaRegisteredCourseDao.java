package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.RegisteredCourseDao;
import cms.academic.academicapp.model.RegisteredCourse;

@Repository
public class JpaRegisteredCourseDao extends JpaAbstractDao<RegisteredCourse> 
 implements RegisteredCourseDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
