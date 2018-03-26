package ch.howard.index.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.howard.index.model.AddressList;

public interface AddressListDAO extends PagingAndSortingRepository<AddressList, Integer> {
	
	public Page<AddressList> findByType(Integer type, Pageable pageable);
	
	@Query("select new AddressList(a.id, a.name, a.sex, a.type, a.phone, a.addTime, a.comment, a.address, a.company) from AddressList a where a.user.id = ?1 and a.type = 0")
	public Page<AddressList> findByUser(Integer userId, Pageable pageable);
	
	@Query("select new AddressList(a.id, a.name, a.sex, a.type, a.phone, a.addTime, a.comment, a.address, a.company) from AddressList a where a.id=?1")
	public AddressList findById(Integer userId);
	
	@Query("select new AddressList(a.id, a.name, a.sex, a.type, a.phone, a.addTime, a.comment, a.address, a.company) from AddressList a where a.name like %?1% or a.company like %?1%")
	public List<AddressList> findByNameOrCompanyContaining(String param);
}
