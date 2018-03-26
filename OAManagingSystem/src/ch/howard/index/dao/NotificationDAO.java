package ch.howard.index.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.index.model.Notification;

public interface NotificationDAO extends PagingAndSortingRepository<Notification, Integer> {
	
	@Query("select new Notification(n.id, n.content, n.updateTime, n.hasSee, n.title) from Notification n where n.id = ?1")
	public Notification findById(Integer id);
	
	@Query("select new Notification(n.id, n.content, n.updateTime, n.hasSee, n.title) from Notification n where n.user.id=?1 ")
	public Page<Notification> findByUserId(Integer uid, Pageable pageable);
	
	public long countByUserIdAndHasSee(Integer uid, Integer hasSee);
}
