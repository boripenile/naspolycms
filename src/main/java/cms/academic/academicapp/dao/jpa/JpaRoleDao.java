package cms.academic.academicapp.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.RoleDao;
import cms.academic.academicapp.model.Role;

@Repository
public class JpaRoleDao extends JpaAbstractDao<Role> implements RoleDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
