package ch.howard.index.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import ch.howard.frame.model.User;
import ch.howard.frame.util.Util;
import ch.howard.index.dao.AddressListDAO;
import ch.howard.index.model.AddressList;

@Service
public class AddressListService {
	private static final transient Logger log = LoggerFactory.getLogger(AddressListService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private AddressListDAO addressListDao;
	/**
	 * 保存新的通讯录
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> saveAddressList(Map<String, Object> inMap) throws Exception {
		AddressList al = (AddressList) Util.mapToObject(inMap, AddressList.class);//Map数据结构直接转换成对象
		if(al.getType() == 0) {
			User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
			al.setUser(new User(u.getId()));
		}
		al.setAddTime(new Date());	
		
		addressListDao.save(al);
		
		outMap.put("message", "添加成功");
		outMap.put("addressList", al);
		return outMap;
	}
	
	/**
	 * 分页找通讯录
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findAddressListPage(Map<String, Object> inMap) {
		Integer type =  (Integer) inMap.get("type");
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "id"));
		Sort sort = new Sort(orders);
		Pageable pageable;
		Page<AddressList> addressLists;
		if(type == 0) {
			Integer num = (Integer) inMap.get("num");
			User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
			pageable = new PageRequest(page, num, sort);
			addressLists = addressListDao.findByUser(u.getId(), pageable);
		}else {
			pageable = new PageRequest(page, 3, sort);
			addressLists = addressListDao.findByType(type, pageable);
		}
		
		outMap.put("addressLists", addressLists.getContent());
		outMap.put("totalPages", addressLists.getTotalPages());
		return outMap;
	}
	
	/**
	 * 根据id查找通讯录
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findAddressListById(Map<String, Object> inMap) {
		String aid = (String) inMap.get("aid");
		AddressList addressList = addressListDao.findById(Integer.valueOf(aid));
		outMap.put("addressList", addressList);
		return outMap;
	}
	
	/**
	 * 通过id修改通讯录
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateAddressListById(Map<String, Object> inMap) {
		String id = (String) inMap.get("id");
		String name = (String) inMap.get("name");
		String company = (String) inMap.get("company");
		String sex = (String) inMap.get("sex");
		String type = (String) inMap.get("type");
		String phone = (String) inMap.get("phone");
		String comment = (String) inMap.get("comment");
		String address = (String) inMap.get("address");
		
		AddressList addressList = addressListDao.findById(Integer.valueOf(id));
		addressList.setCompany(company);addressList.setType(Integer.valueOf(type));addressList.setPhone(phone);
		addressList.setComment(comment);addressList.setAddress(address);
		if(type.equals("1")) { //公司联系人,公司联系人没有性别和不属于其他用户
			addressList.setName("");addressList.setSex(0);addressList.setUser(null);
		}else {
			if(type.equals("2")) { //竞争者，竞争者是拥有性别，但不属于其他用户
				addressList.setUser(null); 
			}else {
				User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
				addressList.setUser(new User(u.getId()));
			}
			addressList.setName(name);addressList.setSex(Integer.valueOf(sex));
		}
		
		addressListDao.save(addressList);
		
		outMap.put("addressList", addressList);
		outMap.put("message", "修改成功");
		return outMap;
	}
	
	/**
	 * 通过通讯录id删除通讯录
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteAddressListById(Map<String, Object> inMap) {
		String id = (String) inMap.get("aid");
		addressListDao.delete(Integer.valueOf(id));
		outMap.put("message", "删除成功");
		return outMap;
	}
	
	
	public Map<String, Object> findAddressListwithNameOrCompany(Map<String, Object> inMap) {
		String param = (String) inMap.get("param");
		
		outMap.put("addressLists", addressListDao.findByNameOrCompanyContaining(param));
		return outMap;
	}
}
