package ch.howard.rbac.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.rbac.model.Menu;

public interface MenuDAO extends CrudRepository<Menu, Integer> {

}
