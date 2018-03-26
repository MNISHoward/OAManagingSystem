package ch.howard.index.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.frame.model.User;
import ch.howard.index.dao.EventDAO;
import ch.howard.index.model.Event;


@Service
public class EventService {
	private static final transient Logger log = LoggerFactory.getLogger(EventService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private EventDAO eventDao;
	
	/**
	 * 通过userid找出该用户的所有事件
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findEventByUserId(Map<String,Object> inMap) {
		String uid = (String) inMap.get("uid");
		List<Event> events = eventDao.findByUserIdNoUserId(Integer.valueOf(uid));
		outMap.put("events", events);
		
		return outMap;
	}
	
	/**
	 * 新增事件
	 * @param inMap
	 * @return
	 * @throws ParseException
	 */
	@Transactional
	public Map<String, Object> insertEvent(Map<String, Object> inMap) throws ParseException {
		String uid = (String) inMap.get("uid");
		String allDay = (String) inMap.get("allDay");
		String content = (String) inMap.get("content");
		String endDate = (String) inMap.get("endDate");
		String startDate = (String) inMap.get("startDate");
		String startTime = (String) inMap.get("startTime");
		
		if(allDay.equals("true")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Event e = new Event(null, content, Boolean.TRUE,sdf.parse(startDate), null);
			e.setUser(new User(Integer.valueOf(uid)));
			eventDao.save(e);
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = startDate+ " " +startTime;
			Event e= new Event(null, content, Boolean.FALSE, sdf.parse(date), sdf.parse(endDate));
			e.setUser(new User(Integer.valueOf(uid)));
			eventDao.save(e);
		}
		
		outMap.put("message", "新增事件成功");
		
		return outMap;
	}
	
	/**
	 * 修改事件
	 * @param inMap
	 * @return
	 * @throws ParseException
	 */
	@Transactional
	public Map<String, Object> updateEvent(Map<String, Object> inMap) throws ParseException {
		String eid = (String) inMap.get("eid");
		String uid = (String) inMap.get("updateUid");
		String allDay = (String) inMap.get("updateAllDay");
		String content = (String) inMap.get("updateContent");
		String endDate = (String) inMap.get("updateEndDate");
		String startDate = (String) inMap.get("updateStartDate");
		String startTime = (String) inMap.get("updateStartTime");
		Event e = eventDao.findById(Integer.valueOf(eid));
		if(allDay.equals("true")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			e.setAllDay(Boolean.TRUE);e.setEnd(null);e.setStart(sdf.parse(startDate));e.setTitle(content);
			e.setUser(new User(Integer.valueOf(uid)));
			eventDao.save(e);
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = startDate+ " " +startTime;
			e.setAllDay(Boolean.FALSE);e.setEnd(sdf.parse(endDate));e.setStart(sdf.parse(date));e.setTitle(content);
			e.setUser(new User(Integer.valueOf(uid)));
			eventDao.save(e);
		}
		
		outMap.put("message", "修改事件成功");
		
		return outMap;
	}
	
	/**
	 * 删除事件
	 * @param inMap
	 * @return
	 */
	public Map<String,Object> deleteEvent(Map<String, Object> inMap) {
		String eid = (String) inMap.get("eid");
		Event e = eventDao.findById(Integer.valueOf(eid));
		eventDao.delete(e);
		outMap.put("message", "删除成功");
		return outMap;
	}
	/**
	 * 查找今天的所有事件
	 * @return
	 * @throws ParseException
	 */
	public Iterable<Event> findAllEventWithUserIdAndDate() throws ParseException {
		User u =(User)SecurityUtils.getSubject().getSession().getAttribute("user");
		Integer uid = u.getId();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		List<Event> events = eventDao.findByUserIdDateNoUser(uid);
		return events;
	}
}
