package cms.academic.academicapp.dao;

import cms.academic.academicapp.model.User;

public interface UserDao extends Dao<User> {
	User findUserByEmailAddress(String emailaddress);
}
