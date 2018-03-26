package ch.howard.index.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.index.model.Event;


public interface EventDAO extends PagingAndSortingRepository<Event, Integer> {
	
	@Query("select new Event(e.id, e.title, e.allDay, e.start, e.end) from Event e where e.user.id = ?1")
	public List<Event> findByUserIdNoUserId(Integer uid);
	
	@Query("select new Event(e.id, e.title, e.allDay, e.start, e.end) from Event e where e.id = ?1")
	public Event findById(Integer id);
	
	@Query(value = "select * from (select id, title, allDay, start, end, user_id from ch_event where user_id =?1 and TO_DAYS(start) - TO_DAYS(NOW()) = 0 union SELECT id, title, allDay, START, END, user_id FROM ch_event WHERE user_id =?1 AND TO_DAYS(START) - TO_DAYS(NOW()) <=0 AND TO_DAYS(END) - TO_DAYS(NOW()) >=0 AND allDay IS FALSE) event ORDER BY START ASC", nativeQuery=true)
	public List<Event> findByUserIdDateNoUser(Integer uid);
	
}
