package ch.howard.asset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.asset.model.Asset;

public interface AssetDAO extends PagingAndSortingRepository<Asset, Integer>{
	
	@Query("select count(*) from Asset a where a.assetType.id = ?1 and a.state = 0")
	public long countSurplusByType(Integer AssetTypeId);
	
	@Query("select count(*) from Asset a where a.assetType.id = ?1 and a.state = 1")
	public long countLendByType(Integer AssetTypeId);
	
	
	public List<Asset> findByIdOrModelContaining(Integer id, String model);
}
