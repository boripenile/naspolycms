package cms.academic.academicapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.dao.jpa.JpaRoleDao;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.service.RoleService;

@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private JpaRoleDao roleDao = null;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public List<Role> findAllRoles() {
		try {
			return roleDao.getAll();						
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Role findById(Long id) {
		try {
			return roleDao.get(id);
		} catch (Exception e) {
			return null;
		}
	}
    
}
