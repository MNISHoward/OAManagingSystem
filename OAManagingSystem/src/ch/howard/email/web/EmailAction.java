package ch.howard.email.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import ch.howard.email.model.Draftbox;
import ch.howard.email.model.Inbox;
import ch.howard.email.model.Outbox;
import ch.howard.email.service.DraftboxService;
import ch.howard.email.service.InboxService;
import ch.howard.email.service.OutboxService;
import ch.howard.frame.web.BaseAction;
import ch.howard.rbac.web.RbacAction;

@Controller
public class EmailAction extends BaseAction implements ModelDriven<Outbox> {
	
	private static final transient Logger log = LoggerFactory.getLogger(RbacAction.class);
	
	@Autowired
	private OutboxService outboxService;
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private DraftboxService draftboxService;
	
	private Outbox outbox;
	
	private String accpetPersonId;
	
	private List<Inbox> inboxs  = new ArrayList<Inbox>();
	
	private List<Outbox> outboxs  = new ArrayList<Outbox>();
	
	private List<Draftbox> draftboxs  = new ArrayList<Draftbox>();
	
	private Integer totalPages;
	
	private String did;
	
	private long inboxCount;
	
	//3是草稿，1是收件箱 ，0是新邮件，2是发件箱
	private Integer flag;
	
	public void setAccpetPersonId(String accpetPersonId) {
		this.accpetPersonId = accpetPersonId;
	}

	public List<Inbox> getInboxs() {
		return inboxs;
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
	
	public List<Outbox> getOutboxs() {
		return outboxs;
	}
	
	public List<Draftbox> getDraftboxs() {
		return draftboxs;
	}
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public void setDid(String did) {
		this.did = did;
	}

	public Outbox getOutbox() {
		return outbox;
	}
	
	public long getInboxCount() {
		return inboxCount;
	}

	@Override
	public String execute() throws Exception {
		//获取所有资源值
		initMethod();
		
		Map<String, Object > inMap = new HashMap<String, Object>();
		inMap.put("page", 0);
		inMap = inboxService.getInboxPage(inMap);
		inboxs = (List<Inbox>) inMap.get("inboxs");
		totalPages = (Integer) inMap.get("totalPages");
		
		inboxCount = inboxService.getInboxCount(0);
		return "success";
	}
	
	public String writeExecute() {
		
		return "write";
	}
	
	
	public String outboxExecute() {
		
		Map<String, Object > inMap = new HashMap<String, Object>();
		inMap.put("page", 0);
		inMap = outboxService.getOutboxPage(inMap);
		outboxs = (List<Outbox>) inMap.get("outboxs");
		totalPages = (Integer) inMap.get("totalPages");
		return "outbox";
	}
	
	public String draftboxExecute() {
		
		Map<String, Object > inMap = new HashMap<String, Object>();
		inMap.put("page", 0);
		inMap = draftboxService.getDraftboxPage(inMap);
		draftboxs = (List<Draftbox>) inMap.get("draftboxs");
		totalPages = (Integer) inMap.get("totalPages");
		return "draftbox";
	}
	
	
	public String sendEmailHandle() {
		try {
			outboxService.sendEmail(outbox, accpetPersonId);
			//如果是草稿箱编辑的，则发送时也要删除草稿箱的内容
			if(did != null && !did.equals("")) {
				Map<String, Object> inMap = new HashMap<String,Object>();
				inMap.put("did", did);
				draftboxService.deleteDraftById(inMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sendEmail";
	}
	
	public String inboxExecute() {
		return "inbox";
	}

	@Override
	public Outbox getModel() {
		if(outbox == null) {
			outbox=new Outbox();
		}
		return outbox;
	}
}
