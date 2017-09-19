package cms.academic.academicapp.dao.jpa;


import static org.springframework.util.Assert.notNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.UserDao;
import cms.academic.academicapp.model.User;

@Repository
public class JpaUserDao extends JpaAbstractDao<User> implements UserDao{
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public User findUserByEmailAddress(String emailaddress) {
		notNull(emailaddress, "Email address can't be null");
		User selected = entityManager.createQuery("SELECT o FROM User o WHERE o.emailaddress = :email", User.class)
			.setParameter("email", emailaddress)
			.getSingleResult();
		if(selected == null){
			return null;
		}
		return selected;
	}
}
