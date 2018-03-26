package ch.howard.frame.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import ch.howard.frame.model.Resource;
import ch.howard.frame.service.ResourceService;
import ch.howard.index.model.Notification;
import ch.howard.index.service.NotificationService;

public class BaseAction extends ActionSupport {
	
	private static final transient Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	@Autowired
	protected ResourceService resourceService;
	@Autowired
	protected NotificationService notificationService;
	
	protected Iterable<Resource> resources;
	
	protected Integer rid;
	protected Resource resource;
	protected Notification notification;
	protected long notificationCount;
	
	public Iterable<Resource> getResources() {
		return resources;
	}
	public void setResources(Iterable<Resource> resources) {
		this.resources = resources;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public long getNotificationCount() {
		return notificationCount;
	}
	public void setNotificationCount(long notificationCount) {
		this.notificationCount = notificationCount;
	}
	protected void initMethod() {
		
		log.info("初始化action， 获取所有的资源项");
		//获取资源项
		resources = resourceService.queryResource();
		
		log.info("获取当前的资源项");
		//获取资源中的菜单项
		if(rid != null) {
			resource = resourceService.queryResourceById(rid, resources);
		}
		
		
		//获取公告项
		notification = notificationService.getNotification();
		
		notificationCount = notificationService.countNotificationById();
		
	}
}
