package ch.howard.administer.service;

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

import ch.howard.administer.dao.DailyDAO;
import ch.howard.administer.model.Daily;
import ch.howard.frame.model.User;
import ch.howard.index.model.DailyModelDriven;

@Service
public class DailyService {
	
	private static final transient Logger log = LoggerFactory.getLogger(DailyService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private DailyDAO dailyDao;
	/**
	 * 分页日报
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> getDailyPage(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "hasSee"));
		orders.add(new Order(Direction.DESC, "updateTime"));
		
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 7, sort);
		Page<Daily> dailyPage;
		if(SecurityUtils.getSubject().hasRole("admin")) {
			dailyPage = dailyDao.findByPage(pageable);
		}else {
			dailyPage = dailyDao.findByParentId(u.getId(), pageable);
		}
		List<Daily> content = dailyPage.getContent();
		for(Daily d : content) {
			d.setContent(HtmlUtils.htmlUnescape(d.getContent()));
		}
		outMap.put("dailys", content);
		outMap.put("totalPages", dailyPage.getTotalPages());
		return outMap;
	}
	
	/**
	 * 分页自己的日报
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> getOwnDailyPage(Map<String, Object> inMap) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User)session.getAttribute("user");
		
		Integer page = (Integer) inMap.get("page");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "hasSee"));
		orders.add(new Order(Direction.DESC, "updateTime"));
		
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, 6, sort);
		Page<Daily> dailyPage;
		dailyPage = dailyDao.findByAuthorId(u.getId(), pageable);
		List<Daily> content = dailyPage.getContent();
		for(Daily d : content) {
			d.setContent(HtmlUtils.htmlUnescape(d.getContent()));
		}
		outMap.put("dailys", content);
		outMap.put("totalPages", dailyPage.getTotalPages());
		return outMap;
	}
	
	/**
	 * 更改未读状态
	 * @param inMap
	 */
	@Transactional
	public void updateDailyById(Map<String, Object> inMap) {
		String did = (String) inMap.get("did");
		Daily daily = dailyDao.findByIdWithAuthorAndLeader(Integer.valueOf(did));
		daily.setHasSee(1);
		dailyDao.save(daily);
	}
	
	/**
	 * 保存和新增日报
	 * @param dailyModelDriven
	 */
	@Transactional
	public void saveDaily(DailyModelDriven dailyModelDriven) {
		Session session = SecurityUtils.getSubject().getSession();
		User u = (User) session.getAttribute("user");
		String summary = dailyModelDriven.getSummary();
		Daily daily = new Daily(null, dailyModelDriven.getTitle(), summary,HtmlUtils.htmlEscape(dailyModelDriven.getMyContent()), new Date(), 0, u.getId(), Integer.valueOf(dailyModelDriven.getLeader()));
		if(dailyModelDriven.getId() != -1) {
			daily.setHasSee(1);
			daily.setId(Integer.valueOf(dailyModelDriven.getId()));
		}
		
		dailyDao.save(daily);
		
	}
}

