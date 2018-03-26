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

import ch.howard.email.dao.OutboxDAO;
import ch.howard.email.model.Draftbox;
import ch.howard.email.model.Inbox;
import ch.howard.email.model.Outbox;
import ch.howard.frame.model.User;
import ch.howard.frame.util.Util;

@Service
public class OutboxService {
	private static final transient Logger log = LoggerFactory.getLogger(OutboxService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private OutboxDAO outboxDao;
	@Autowired
	private InboxService inboxService;
	
	
	/**
	 * 发邮件功能
	 * @param outbox
	 * @param accpetPersonId
	 * @throws Exception
	 */
	@Transactional
	public void sendEmail(Outbox outbox, String accpetPersonId) throws Exception {
		outbox.setId(null);
		//保存发信箱
		if(outbox.getTitle() == null) {
			if(outbox.getSummary().length() > 10)
			outbox.setTitle(outbox.getSummary().substring(0, 10));
		}
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		outbox.setAcceptDetail(HtmlUtils.htmlEscape(outbox.getAcceptDetail().trim()));
		outbox.setContent(HtmlUtils.htmlEscape(outbox.getContent()));
		outbox.setSendPerson(new User(u.getId()));
		outbox.setSendTime(new Date());
		
		//保存收信箱
		Map<String, Object> map = (Map<String, Object>) Util.objectToMap(outbox);
		Inbox inbox = (Inbox) Util.mapToObject(map, Inbox.class);
		
		this.saveOutbox(outbox);
		String[] split = accpetPersonId.split(",");
		for(String s : split) {
			inbox.setAcceptPerson(new User(Integer.valueOf(s)));
			inboxService.saveInbox(inbox);
		}
		
	}
	
	/**
	 * 分页
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> getOutboxPage(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "sendTime"));
		
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 13, sort);
		Page<Outbox> outboxPage = outboxDao.findBySendPersonId(u.getId(), pageable);
		List<Outbox> content = outboxPage.getContent();
		for(Outbox d : content) {
			d.setAcceptDetail(HtmlUtils.htmlUnescape(d.getAcceptDetail()));
			d.setContent(HtmlUtils.htmlUnescape(d.getContent()));
		}
		outMap.put("outboxs", content);
		outMap.put("totalPages", outboxPage.getTotalPages());
		return outMap;
	}
	
	/**
	 * 通过id删除草稿箱
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteOutById(Map<String, Object> inMap) {
		String oid = (String) inMap.get("oid");
		Outbox o = new Outbox();
		o.setId(Integer.valueOf(oid));
		outboxDao.delete(o);
		
		outMap.put("message", "删除成功");
		
		return outMap;
	}
	
	
	@Transactional
	public void saveOutbox(Outbox outbox) {
		
		outboxDao.save(outbox);
	}
	
}
