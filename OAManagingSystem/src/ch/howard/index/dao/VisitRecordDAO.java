package ch.howard.index.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.index.model.VisitRecord;

public interface VisitRecordDAO extends PagingAndSortingRepository<VisitRecord, Integer>{
	
	@Query("select new VisitRecord(v.name, v.visitTime) from VisitRecord v where v.user.id = ?1")
	public Page<VisitRecord> findVisitRecordByUserId(Integer userId, Pageable pageable);
	
}
