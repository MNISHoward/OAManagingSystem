package ch.howard.asset.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.asset.model.AssetType;

public interface AssetTypeDAO extends PagingAndSortingRepository<AssetType, Integer> {
	
}
