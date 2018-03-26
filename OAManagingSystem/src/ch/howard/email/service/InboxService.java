package ch.howard.email.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import org.springframework.web.util.HtmlUtils;

import ch.howard.email.dao.InboxDAO;
import ch.howard.email.model.Inbox;
import ch.howard.frame.model.User;
import ch.howard.frame.util.EhcacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class InboxService {
	private static final transient Logger log = LoggerFactory.getLogger(InboxService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private InboxDAO inboxDao;
	
	@Transactional
	public void saveInbox(Inbox inbox) {
		Cache cache = EhcacheUtil.getCache("resourceCache");
		cache.put(new Element("user" + inbox.getAcceptPerson().getId(), true));
		inboxDao.save(inbox);
	}
	
	/**
	 * 分页
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> getInboxPage(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "sendTime"));
		
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 13, sort);
		Page<Inbox> inboxPage = inboxDao.findByAccpetPersonId(u.getId(), pageable);
		List<Inbox> content = inboxPage.getContent();
		for(Inbox d : content) {
			d.setContent(HtmlUtils.htmlUnescape(d.getContent()));
		}
		outMap.put("inboxs", content);
		outMap.put("totalPages", inboxPage.getTotalPages());
		return outMap;
	}
	
	/**
	 * 更新查看状态
	 * @param inMap
	 */
	@Transactional
	public void updateInboxHasSee(Map<String, Object> inMap) {
		String iid = (String) inMap.get("iid");
		Inbox inbox = inboxDao.findOne(Integer.valueOf(iid));
		inbox.setHasSee(1);
		saveInbox(inbox);
	}
	
	/**
	 * 通过id删除草稿箱
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteInById(Map<String, Object> inMap) {
		String iid = (String) inMap.get("iid");
		Inbox i = new Inbox();
		i.setId(Integer.valueOf(iid));
		inboxDao.delete(i);
		
		outMap.put("message", "删除成功");
		
		return outMap;
	}
	
	/**
	 * 获取收件箱的数量
	 * @param hasSee
	 * @return
	 */
	public long getInboxCount(Integer hasSee) {
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		return inboxDao.countByHasSeeAndAcceptPersonId(hasSee, u.getId());
	}
	/**
	 * 定时查询收信箱
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> intervalGetInbox(Map<String, Object> inMap) {
		Cache cache = EhcacheUtil.getCache("resourceCache");
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Element element = cache.get("user" + u.getId());
		if(element != null && (Boolean)element.getObjectValue()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", 0);
			this.getInboxPage(map);
			outMap.put("inboxCount", this.getInboxCount(0));
			cache.put(new Element("user" + u.getId(), false));
			outMap.put("hasChange", true);
		}else {
			outMap.put("hasChange", false);
		}
		return outMap;
	}
	
}
