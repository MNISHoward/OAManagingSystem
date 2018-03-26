package ch.howard.rbac.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.howard.rbac.model.Role;

public interface RoleDAO extends CrudRepository<Role, Integer>{
	
	@Query("select new ch.howard.rbac.model.Role(r.id, r.titleName, r.state) from Role r")
	public List<Role> findAllRoleNoMenuAndUser();
	
	public Set<Role> findByIdIn(Collection<Integer> ids);

	public Role findById(Integer id);
	
}
