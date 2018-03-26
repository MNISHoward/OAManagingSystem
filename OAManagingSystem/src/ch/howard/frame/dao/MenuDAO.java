package ch.howard.frame.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.frame.model.Menu;

public interface MenuDAO extends PagingAndSortingRepository<Menu, Integer> {
	
	
	@Query("select new Menu(m.id, m.state,m.titleName, m.url) from Menu m")
	public Iterable<Menu> findAllMenuNoResourceAndRoles();
	
	@Query("select new Menu(m.id, m.state,m.name,m.titleName, m.url, m.resource.id, m.iconClass) from Menu m where m.id = ?1")
	public Menu findMenuNoRoles(Integer id);
}
