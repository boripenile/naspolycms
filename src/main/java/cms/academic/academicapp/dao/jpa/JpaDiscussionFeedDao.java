package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.DiscussionFeedDao;
import cms.academic.academicapp.model.DiscussionFeed;

@Repository
public class JpaDiscussionFeedDao extends JpaAbstractDao<DiscussionFeed> implements DiscussionFeedDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
