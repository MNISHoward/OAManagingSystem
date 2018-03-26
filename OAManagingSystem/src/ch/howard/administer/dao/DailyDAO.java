package ch.howard.administer.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.administer.model.Daily;

public interface DailyDAO extends PagingAndSortingRepository<Daily, Integer> {
	
	@Query("select new Daily(d.id,d.title,d.summary,d.content,d.updateTime,d.hasSee,d.author.staff.titleName) from Daily d")
	public Page<Daily> findByPage(Pageable pageable);
	
	@Query("select new Daily(d.id,d.title,d.summary,d.content,d.updateTime,d.hasSee,d.author.staff.titleName) from Daily d where d.leader.id=?1")
	public Page<Daily> findByParentId(Integer parentid, Pageable pageable);
	
	@Query("select new Daily(d.id,d.title,d.summary,d.content,d.updateTime,d.hasSee,d.author.id,d.leader.id) from Daily d where d.id = ?1")
	public Daily findByIdWithAuthorAndLeader(Integer id);
	
	@Query("select new Daily(d.id,d.title,d.summary,d.content,d.updateTime,d.hasSee,d.author.staff.titleName) from Daily d where d.author.id=?1")
	public Page<Daily> findByAuthorId(Integer authorid, Pageable pageable);
}
