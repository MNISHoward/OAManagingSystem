package ch.howard.rbac.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.rbac.model.Resource;

public interface ResourceDAO extends CrudRepository<Resource, Integer> {

}
