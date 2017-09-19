package cms.academic.academicapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaCourseDao;
import cms.academic.academicapp.dao.jpa.JpaRegisteredCourseDao;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.MemberCourseService;

@Transactional(propagation = Propagation.REQUIRED)
public class MemberCourseServiceImpl implements MemberCourseService {

	@Autowired
	private JpaRegisteredCourseDao registeredCourseDao = null;

	@Autowired
	private JpaCourseDao courseDao = null;

	public Boolean isCourseRegistered(Course course) {
		try {
			List<Course> courses = courseDao.getAll();
			if (courses != null) {
				for (Course c : courses) {
					if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public Integer registerCourse(List<RegisteredCourse> memberCourses) {
		try {
			int result = 0;
			if (memberCourses != null) {
				for (RegisteredCourse c : memberCourses) {
					registeredCourseDao.create(c);
					result++;
				}
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public List<User> viewCourseFacultyMembers(Course course) {
		try {
			List<RegisteredCourse> list = registeredCourseDao.getAll();
			List<User> members = new ArrayList<User>();
			if (list != null) {
				for (RegisteredCourse r : list) {
					if (r.getCourse().equals(course)) {
						members.add(r.getMember());
					}
				}
			}
			return members;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<RegisteredCourse> registeredCourses(User member) {
		try {
			List<RegisteredCourse> list = registeredCourseDao.getAll();
			List<RegisteredCourse> registered = new ArrayList<RegisteredCourse>();
			if (list != null) {
				for (RegisteredCourse r : list) {
					if (r.getMember().equals(member)) {
						registered.add(r);
					}
				}
			}
			return registered;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer totalRegisteredCourse(User member) {
		try {
			int result = 0;
			List<RegisteredCourse> list = registeredCourseDao.getAll();
			if (list != null) {
				for (RegisteredCourse r : list) {
					if (r.getMember().equals(member)) {
						result++;
					}
				}
			}
			return result;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer removeRegisteredCourse(RegisteredCourse course) {
		try {
			if (course != null) {
				registeredCourseDao.deleteById(course.getId());
				return 1;
			}
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	@Override
	public RegisteredCourse viewRegisteredCourse(Long course) {
		try {
			if (course != null) {
				return registeredCourseDao.get(course);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public List<RegisteredCourse> allRegisteredCourses() {
		try {
			return registeredCourseDao.getAll();
		} catch (Exception e) {
			return null;
		}
	}

}
