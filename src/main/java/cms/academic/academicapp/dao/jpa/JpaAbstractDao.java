package cms.academic.academicapp.dao.jpa;

import static org.springframework.util.Assert.notNull;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import cms.academic.academicapp.dao.Dao;


public abstract class JpaAbstractDao<T extends Object> implements Dao<T>{
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private Class<T> domainClass;
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
	
	private String getDomainClassName() { return getDomainClass().getName(); }
	
	@Override
	public void create(T t) {
		notNull(t, "Entity can't be null");
		entityManager.persist(t);		
	}

	@Override
	public T get(Serializable id) {
        notNull(id, "id can't be null");
		
		// This returns null when the object doesn't exist
        try {
        	return entityManager.find(getDomainClass(), id);
		} catch (Exception e) {
			return null;
		}	
	}

	@Override
	public T load(Serializable id) {
        notNull(id, "id can't be null");
		
		// TODO Check whether there's a JPA equivalent to Hibernate load()
		T t = get(id);
		if (t == null) {
			throw new RuntimeException("No such entity: " + id);
		}
		return t;
	}

	@Override
	public List<T> getAll() {
		try {
			return entityManager.createQuery("SELECT o FROM "+ getDomainClassName() + " o", getDomainClass()).getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<T> getAllEntries(int firstResult, int maxResults) {
		try {
			return entityManager.createQuery("SELECT o FROM "+ getDomainClassName() + " o", getDomainClass())
					.setFirstResult(firstResult)
					.setMaxResults(maxResults)
					.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}		
	}

	@Override
	public T update(T t) {
		T merged = entityManager.merge(t);		
		return merged;
	}

	@Override
	public void deleteAll() {
		entityManager
		.createQuery("DELETE o FROM " + getDomainClassName())
		.executeUpdate();		
	}
	
	@Override
	public void deleteById(Serializable id) {
		notNull(id, "id can't be null");
		T attached = get(id);
		entityManager.remove(attached);
	}

	@Override
	public long count() {
		return entityManager.createQuery("SELECT COUNT(o) FROM " + getDomainClassName()  + " o", Long.class).getSingleResult();
	}

	@Override
	public boolean exists(Serializable id) {
		notNull(id, "id can't be null");
		return (get(id) != null);
	}
     
	public void flush(){
		entityManager.flush();
		entityManager.clear();
	}

}
