package ch.howard.rbac.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.rbac.model.Role;

public interface RoleDAO extends CrudRepository<Role, Integer>{
	
}
