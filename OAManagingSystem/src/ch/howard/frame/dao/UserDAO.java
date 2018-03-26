package ch.howard.frame.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.User;
import ch.howard.rbac.model.Role;

public interface UserDAO extends CrudRepository<User, Integer> {
	
	public User findByUsername(String username);
	
	public User findById(Integer id);
	
	public User findByStaffId(Integer sid);
	
	@Modifying
	@Query("update User u set u.password = ?2 where u.id = ?1")
	public void updateByIdSetPass(Integer id, String password);
	
	@Query("select new User(u.id,u.username,u.state) from User u")
	public Iterable<User> findAllUserNoStaffAndRoles();
	
	@Query("select new User(u.id) from User u")
	public Iterable<User> findAllId();
	
	@Query("select new User(u.id,u.staff.id,u.staff.titleName) from User u where u.staff.id = ?1 or u.staff.name like %?2%")
	public List<User> findByIdOrNameContaining(Integer id, String name);
	
	@Query("select new User(u.id,u.staff.titleName,u.staff.email) from User u where u.staff.name like %?1% or u.staff.titleName like %?1% or u.staff.email like %?1%")
	public List<User> findByNameOrEmailContaining(String name);
}
