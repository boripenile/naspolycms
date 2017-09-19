package cms.academic.academicapp.service;

import java.util.List;

import cms.academic.academicapp.model.Role;

public interface RoleService {
	public List<Role> findAllRoles();
	
	public Role findById(Long id);
}
