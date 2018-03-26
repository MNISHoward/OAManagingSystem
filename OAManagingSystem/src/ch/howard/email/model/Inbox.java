package ch.howard.email.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ch.howard.frame.model.User;
import ch.howard.rbac.model.Staff;

@Entity
@Table(name="ch_inbox")
public class Inbox {
	private Integer id;
	private String title;
	private String content;
	private User sendPerson;
	private User acceptPerson;
	private Date sendTime;
	private Integer state;
	private Integer hasSee = new Integer(0) ;
	private String summary;
	
	
	public Inbox() {
		super();
	}
	public Inbox(Integer id, String title, String content, Date sendTime, Integer hasSee, String summary,String titleName, String email) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.sendTime = sendTime;
		this.hasSee = hasSee;
		this.summary = summary;
		this.sendPerson = new User();
		sendPerson.setStaff(new Staff());
		sendPerson.getStaff().setTitleName(titleName);
		sendPerson.getStaff().setEmail(email);
	}
	
	@Lob
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Lob
	@Column(length = 16777216)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="sendPerson")
	public User getSendPerson() {
		return sendPerson;
	}
	public void setSendPerson(User sendPerson) {
		this.sendPerson = sendPerson;
	}
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="accpetPerson")
	public User getAcceptPerson() {
		return acceptPerson;
	}
	public void setAcceptPerson(User acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getHasSee() {
		return hasSee;
	}
	public void setHasSee(Integer hasSee) {
		this.hasSee = hasSee;
	}
	
	
	
}
