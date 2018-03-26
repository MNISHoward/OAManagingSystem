package ch.howard.email.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.email.model.Inbox;

public interface InboxDAO extends PagingAndSortingRepository<Inbox, Integer> {
	
	@Query("select new Inbox(i.id, i.title, i.content, i.sendTime, i.hasSee, i.summary, i.sendPerson.staff.titleName, i.sendPerson.staff.email) from Inbox i where i.acceptPerson.id = ?1")
	public Page<Inbox> findByAccpetPersonId(Integer apId, Pageable pageable);
	
	public long countByHasSeeAndAcceptPersonId(Integer hasSee, Integer aid);
	
}
