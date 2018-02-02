package ch.howard.frame.dao;

import org.springframework.data.repository.CrudRepository;

import ch.howard.frame.model.Menu;

public interface MenuDAO extends CrudRepository<Menu, Integer> {

}
