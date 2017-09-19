package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.User;


public interface UserService {
    public Boolean registerUser(User newUser);
    
    public List<User> users();
    
    public Boolean logUser(String emailAdress, String password);
    
    public User findByEmailAddress(String emailAddress);
    
    public User findById(Long Id);
    
    public void updateUser(User user);
    
}
