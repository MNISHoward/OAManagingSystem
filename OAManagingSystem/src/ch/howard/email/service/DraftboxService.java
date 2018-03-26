package ch.howard.email.service;

import java.util.ArrayList;
import java.util.Date;
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

import ch.howard.email.dao.DraftboxDAO;
import ch.howard.email.model.Draftbox;
import ch.howard.frame.model.User;
import ch.howard.frame.util.Util;

@Service
public class DraftboxService {
	private static final transient Logger log = LoggerFactory.getLogger(DraftboxService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private DraftboxDAO draftboxDao;
	
	
	public void saveDraftbox(Draftbox draftbox) {
		draftboxDao.save(draftbox);
	}
	
	
	/**
	 * 保存到草稿箱
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String,Object> saveDraftboxInSendEmail(Map<String, Object> inMap) throws Exception {
		String did = (String) inMap.getOrDefault("did", null);
		String acceptDetail = (String) inMap.get("acceptDetail");
		String content = (String) inMap.get("content");
		Draftbox db = null;
		if(did == null || did.equals("")) {
			db = (Draftbox) Util.mapToObject(inMap, Draftbox.class);
		}else {
			db = draftboxDao.findOne(Integer.valueOf(did));
			String summary = (String) inMap.get("summary");
			String title = (String) inMap.get("title");
			db.setAcceptDetail(acceptDetail);db.setContent(content);db.setSummary(summary);db.setTitle(title);
		}
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		db.setSendPerson(new User(u.getId()));
		db.setSaveTime(new Date());
		db.setAcceptDetail(HtmlUtils.htmlEscape(acceptDetail));
		db.setContent(HtmlUtils.htmlEscape(content));
		this.saveDraftbox(db);
		
		outMap.put("did", db.getId());
		outMap.put("message", "保存成功");
		return outMap;
	}
	
	
	/**
	 * 分页
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> getDraftboxPage(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "saveTime"));
		
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 7, sort);
		Page<Draftbox> draftboxPage = draftboxDao.findBySendPersonId(u.getId(), pageable);
		List<Draftbox> content = draftboxPage.getContent();
		for(Draftbox d : content) {
			d.setAcceptDetail(HtmlUtils.htmlUnescape(d.getAcceptDetail()));
			d.setContent(HtmlUtils.htmlUnescape(d.getContent()));
		}
		outMap.put("draftboxs", content);
		outMap.put("totalPages", draftboxPage.getTotalPages());
		return outMap;
	}
	
	/**
	 * 通过id删除草稿箱
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteDraftById(Map<String, Object> inMap) {
		String did = (String) inMap.get("did");
		Draftbox d = new Draftbox();
		d.setId(Integer.valueOf(did));
		draftboxDao.delete(d);
		
		outMap.put("message", "删除成功");
		
		return outMap;
	}
	
}
