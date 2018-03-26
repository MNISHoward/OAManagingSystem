package ch.howard.index.service;

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

import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.User;
import ch.howard.index.dao.NotificationDAO;
import ch.howard.index.model.Notification;

@Service
public class NotificationService {
	private static final transient Logger log = LoggerFactory.getLogger(NotificationService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@Autowired
	private UserDAO userDao;
	
	/**
	 * 返回公告
	 * @return
	 */
	public Notification getNotification() {
		Notification notification = notificationDAO.findById(1);
		notification.setContent(HtmlUtils.htmlUnescape(notification.getContent()));
		return notification;
	}
	/**
	 * 更新公告
	 * @param content
	 */
	@Transactional
	public void insertNotificationIdOne(String content) {
		content = HtmlUtils.htmlEscape(content);
		Notification notification = notificationDAO.findById(1);
		if(notification == null) {
			notification = new Notification(1, content, new Date(), 0, "公告");
		}else {
			//重新改变公告的属性
			notification.setContent(content);notification.setHasSee(0);notification.setUpdateTime(new Date());
		}
		
		log.info("更新公告成功");
		notificationDAO.save(notification);
		
		//给所有用户发送公告更新
		this.insertNewNotification("【系统通知】新的公告已经更新", "新的公告内容：" + HtmlUtils.htmlUnescape(notification.getContent()));
	}
	
	/**
	 * 获取当前用户的所有通知
	 * @return
	 */
	public Map<String, Object> getNotificationWithUserId(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "hasSee"));
		orders.add(new Order(Direction.DESC, "updateTime"));
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 5, sort);
		Page<Notification> notificationPage = notificationDAO.findByUserId(u.getId(), pageable);
		
		outMap.put("notifications", notificationPage.getContent());
		outMap.put("totalPages", notificationPage.getTotalPages());
		return outMap;
	}
	/**
	 * 更新已经看的状态
	 * @param inMap
	 */
	@Transactional
	public void updateNotificationById(Map<String, Object> inMap) {
		String nid = (String) inMap.get("nid");
		Notification notification = notificationDAO.findById(Integer.valueOf(nid));
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
		notification.setHasSee(1);
		notification.setUser(user);
		notificationDAO.save(notification);
	}
	/**
	 * 删除通知
	 * @param inMap
	 */
	@Transactional
	public void deleteNotificationById(Map<String, Object> inMap) {
		String nid = (String) inMap.get("nid");
		Notification notification = notificationDAO.findById(Integer.valueOf(nid));
		notificationDAO.delete(notification);
	}
	
	public long countNotificationById() {
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		return notificationDAO.countByUserIdAndHasSee(u.getId(), 0);
	}
	
	/**
	 * 单个用户的通知
	 * @param titleName
	 * @param content
	 * @param uid
	 */
	@Transactional
	public void insertNewNotification(String titleName, String content, Integer uid) {
		Notification notification = new Notification(null, content, new Date(), 0, titleName);
		User u = new User(uid);
		notification.setUser(u);
		notificationDAO.save(notification);
		log.info("【新增通知】用户id为" + uid + "，标题：" +titleName + "，内容：" + content);
	}
	/**
	 * 所有用户的通知，测试暂时不用临时表，优化时候使用，先插入临时表， 再临时表一对一插入
	 * @param titleName
	 * @param content
	 */
	public void insertNewNotification(String titleName, String content) {
		List<Notification> notifications = new ArrayList<Notification>();
		Iterable<User> userIds = userDao.findAllId();
		for(User u : userIds) {
			Notification notification = new Notification(null, content, new Date(), 0, titleName);
			notification.setUser(u);
			notifications.add(notification);
		}
		notificationDAO.save(notifications);
		log.info("【新增通知】全体用户" + "，标题：" +titleName + "，内容：" + content);
	}
	
}
