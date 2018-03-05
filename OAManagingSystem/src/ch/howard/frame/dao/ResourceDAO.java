package ch.howard.frame.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Resource;

public interface ResourceDAO extends CrudRepository<Resource, Integer> {
	
	@Query("select new Resource(id, name, titleName, url) from Resource where id = ?1")
	public Resource findById(Integer id);
}
