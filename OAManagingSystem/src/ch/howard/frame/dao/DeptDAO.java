package ch.howard.frame.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Department;

public interface DeptDAO extends CrudRepository<Department, Integer> {

	@Query(value = "select * from ch_dept where dept_id is null", nativeQuery=true)
	public Iterable<Department> findByDepartmentsIsNull();
}
