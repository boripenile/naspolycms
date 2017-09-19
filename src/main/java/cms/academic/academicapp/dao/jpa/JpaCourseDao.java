package cms.academic.academicapp.dao.jpa;

import static org.springframework.util.Assert.notNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cms.academic.academicapp.dao.CourseDao;
import cms.academic.academicapp.model.Course;

@Repository
public class JpaCourseDao extends JpaAbstractDao<Course> implements CourseDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Override
	public Course findByCode(String code) {
		notNull(code, "Course code can't be null");
		Course selected = entityManager.createQuery("SELECT o FROM Course o WHERE o.courseCode = :code", Course.class)
			.setParameter("code", code)
			.getSingleResult();
		if(selected == null){
			return null;
		}
		return selected;
	}
}
