package ch.howard.email.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.email.model.Outbox;

public interface OutboxDAO extends PagingAndSortingRepository<Outbox, Integer>{
	
	@Query("select new Outbox(o.id, o.title, o.content, o.acceptDetail, o.sendTime, o.state, o.summary) from Outbox o where o.sendPerson.id=?1")
	public Page<Outbox> findBySendPersonId(Integer id, Pageable pageable);
	
	
}
