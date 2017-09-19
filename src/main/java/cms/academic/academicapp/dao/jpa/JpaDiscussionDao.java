package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.DiscussionDao;
import cms.academic.academicapp.model.Discussion;

@Repository
public class JpaDiscussionDao extends JpaAbstractDao<Discussion> implements DiscussionDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
