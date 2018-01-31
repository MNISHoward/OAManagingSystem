package ch.howard.frame.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Staff;

public interface StaffDAO extends CrudRepository<Staff, Integer> {
	
	public Staff findByName(String name);
	
	public Staff findById(Integer id);
	
	@Modifying
	@Query("update Staff s set s.email = ?1, s.phone = ?2, s.address = ?3 where s.id = ?4")
	public void updateByIdSetEmailAndPhoneAndAddress(String email, String phone, String address, Integer id);
}
