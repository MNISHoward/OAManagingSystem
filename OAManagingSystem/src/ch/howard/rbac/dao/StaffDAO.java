package ch.howard.rbac.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.rbac.model.Department;
import ch.howard.rbac.model.Staff;

public interface StaffDAO extends PagingAndSortingRepository<Staff, Integer> {
	
	public Staff findByName(String name);
	
	public Staff findById(Integer id);
	
	@Modifying
	@Query("update Staff s set s.email = ?1, s.phone = ?2, s.address = ?3 where s.id = ?4")
	public void updateByIdSetEmailAndPhoneAndAddress(String email, String phone, String address, Integer id);
	
	public Page<Staff> findByDepartment(Pageable pageable, Department dept);
	
	
}
