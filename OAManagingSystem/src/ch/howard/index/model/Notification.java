package ch.howard.index.model;

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

@Table(name="ch_notification")
@Entity
public class Notification {
	private Integer id;
	private String title;
	private String content;
	private Date updateTime;
	/*
	 * 0未看
	 * 1已看
	 */
	private Integer hasSee = new Integer(0);
	private User user;
	
	
	public Notification() {
		super();
	}
	public Notification(Integer id, String content, Date updateTime, Integer hasSee, String title) {
		super();
		this.id = id;
		this.content = content;
		this.updateTime = updateTime;
		this.hasSee = hasSee;
		this.title = title;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Lob
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getHasSee() {
		return hasSee;
	}
	public void setHasSee(Integer hasSee) {
		this.hasSee = hasSee;
	}
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
