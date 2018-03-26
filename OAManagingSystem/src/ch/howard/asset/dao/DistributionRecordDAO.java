package ch.howard.asset.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.asset.model.DistributionRecord;

public interface DistributionRecordDAO extends PagingAndSortingRepository<DistributionRecord, Integer> {
	
	@Query("select count(*) from DistributionRecord d where d.asset.id = ?1 and d.state = 0")
	public long RecordCount(Integer assetId);
	
}
