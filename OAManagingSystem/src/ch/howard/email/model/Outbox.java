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

@Entity
@Table(name="ch_outbox")
public class Outbox {
	private Integer id;
	private String title;
	private String content;
	private User sendPerson;
	private String acceptDetail;
	private Date sendTime;
	private Integer state = 0;
	private String summary;
	
	
	
	public Outbox() {
		super();
	}
	public Outbox(Integer id, String title, String content, String acceptDetail, Date sendTime, Integer state,
			String summary) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.acceptDetail = acceptDetail;
		this.sendTime = sendTime;
		this.state = state;
		this.summary = summary;
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
	@Lob
	@Column(length = 16777215)
	public String getAcceptDetail() {
		return acceptDetail;
	}
	public void setAcceptDetail(String acceptDetail) {
		this.acceptDetail = acceptDetail;
	}
	
}
