package ch.howard.frame.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ch.howard.frame.model.User;

public interface UserDAO extends CrudRepository<User, Integer> {
	
	public User findByUsername(String username);
	
	public User findById(Integer id);
	
	@Modifying
	@Query("update User u set u.password = ?2 where u.id = ?1")
	public void updateByIdSetPass(Integer id, String password);
}
