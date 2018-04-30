package ch.howard.frame.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import ch.howard.administer.model.Daily;
import ch.howard.administer.service.DailyService;
import ch.howard.index.model.AddressList;
import ch.howard.index.model.DailyModelDriven;
import ch.howard.index.model.Event;
import ch.howard.index.model.VisitRecord;
import ch.howard.index.service.AddressListService;
import ch.howard.index.service.EventService;
import ch.howard.index.service.VisitRecordService;


@Controller
public class IndexAction extends BaseAction implements ModelDriven<DailyModelDriven>{
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	private EventService eventService;
	@Autowired
	private DailyService dailyService;
	@Autowired
	private AddressListService addressListService;
	@Autowired
	private VisitRecordService visitRecordService;
	
	private Iterable<Event> events;
	private List<Daily> dailys;
	private int totalPages;
	private Map<String, Object> addressLists;
	private DailyModelDriven dailyModelDriven = new DailyModelDriven();
	
	public Iterable<Event> getEvents() {
		return events;
	}
	
	public List<Daily> getDailys() {
		return dailys;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public Map<String, Object> getAddressLists() {
		return addressLists;
	}

	public DailyModelDriven getDailyModelDriven() {
		return dailyModelDriven;
	}

	public void setDailyModelDriven(DailyModelDriven dailyModelDriven) {
		this.dailyModelDriven = dailyModelDriven;
	}

	@Override
	public String execute() throws Exception {
		log.info("执行IndexAction的execute开始");
		//获取所有资源
		initMethod();
		
		if(rid == null) {
			rid=1;
		}
		
		events = eventService.findAllEventWithUserIdAndDate();
		
		//私人联系人
		addressLists = new HashMap<String, Object>();
		Map<String, Object> inMap = new HashMap<String, Object>();
		inMap.put("type", 0);
		inMap.put("page", 0);
		inMap.put("num", 3);
		inMap = addressListService.findAddressListPage(inMap);
		addressLists.put("contact", inMap.getOrDefault("addressLists", new ArrayList<AddressList>()));
		addressLists.put("contactPage", inMap.get("totalPages"));
		
		return "success";
	}
	
	public String toDoExecute() {
		return "error";
	}
	
	public String calendarExecute() {
		
		return "calendar";
	}
	
	public String notificationExecute() {
		notification = notificationService.getNotification();
		return "notification";
	}
	
	public String notificationHander() {
		notificationService.insertNotificationIdOne(dailyModelDriven.getMyContent());
		return "notiHandle";
	}
	
	
	public String myDailyExecute() {
		Map<String, Object> inMap = new HashMap<String, Object>();
		inMap.put("page", 0);
		inMap = dailyService.getOwnDailyPage(inMap);
		dailys = (List<Daily>) inMap.get("dailys");
		totalPages = (int) inMap.get("totalPages");
		return "myDaily";
	}

	public String myDailyHander() {
		dailyService.saveDaily(dailyModelDriven);
		return "notiHandle";
	}

	public String addressListExecute() {
		Map<String, Object> outMap;
		Map<String, Object> inMap = new HashMap<String, Object>();
		addressLists = new HashMap<String, Object>();
		inMap.put("page", 0);
		
		//私人联系人
		inMap.put("type", 0);
		inMap.put("num", 6);
		outMap = addressListService.findAddressListPage(inMap);
		addressLists.put("contact", outMap.getOrDefault("addressLists", new ArrayList<AddressList>()));
		addressLists.put("contactPage", outMap.get("totalPages"));
		
		//公司
		inMap.put("type", 1);
		outMap = addressListService.findAddressListPage(inMap);
		addressLists.put("company", outMap.getOrDefault("addressLists", new ArrayList<AddressList>()));
		addressLists.put("companyPage", outMap.get("totalPages"));
		
		//竞争人
		inMap.put("type", 2);
		outMap = addressListService.findAddressListPage(inMap);
		addressLists.put("competitor",  outMap.getOrDefault("addressLists", new ArrayList<AddressList>()));
		addressLists.put("competitorPage", outMap.get("totalPages"));
		
		//拜访记录
		addressLists.put("visit", visitRecordService.findVisitRecord());
		
		return "addressList";
	}
	
	
	
	@Override
	public DailyModelDriven getModel() {
		if(dailyModelDriven == null) {
			dailyModelDriven = new DailyModelDriven();
		}
		return dailyModelDriven;
	}
	
}
