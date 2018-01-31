package ch.howard.frame.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Department;

public interface DeptDAO extends CrudRepository<Department, Integer> {

}
