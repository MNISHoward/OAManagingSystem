package ch.howard.email.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.email.model.Draftbox;
import ch.howard.email.model.Outbox;

public interface DraftboxDAO extends PagingAndSortingRepository<Draftbox, Integer> {
	
	@Query("select new Draftbox(d.id,d.title,d.content,d.acceptDetail, d.saveTime ,d.state,d.summary) from Draftbox d where d.id = ?1")
	public Draftbox findById(Integer id);
	
	
	@Query("select new Draftbox(d.id, d.title, d.content, d.acceptDetail, d.saveTime, d.state, d.summary) from Draftbox d where d.sendPerson.id=?1")
	public Page<Draftbox> findBySendPersonId(Integer id, Pageable pageable);
	
}
