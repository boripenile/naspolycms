package cms.academic.academicapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaUserDao;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.UserService;


@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    
	@Autowired
	private JpaUserDao userDao = null;
	
	public Boolean isUserExits(String emailAddress) {
		try {
			User user = userDao.findUserByEmailAddress(emailAddress);
			if(user != null){
				return true;
			}
		} catch (Exception e) {
			return false;
		}		
		return false;   
    }

	@Override
	public Boolean registerUser(User newUser) {
		try {
			if(!isUserExits(newUser.getEmailaddress())){
				userDao.create(newUser);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<User> users() {
		try {
			return userDao.getAll();
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	public Boolean logUser(String emailAddress, String password) {
		try {
			if(isUserExits(emailAddress)){
				User u = userDao.findUserByEmailAddress(emailAddress);
				if(u.getPassword().equals(password)){
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return null;
	}

	@Override
	public User findByEmailAddress(String emailAddress) {
		try {
			User user = userDao.findUserByEmailAddress(emailAddress);
			if(user != null){
				return user;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public User findById(Long Id) {
		return userDao.load(Id);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}
}
