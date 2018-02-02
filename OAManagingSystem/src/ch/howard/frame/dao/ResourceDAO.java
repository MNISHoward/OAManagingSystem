package ch.howard.frame.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Resource;

public interface ResourceDAO extends CrudRepository<Resource, Integer> {

}
