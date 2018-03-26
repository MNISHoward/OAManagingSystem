package ch.howard.administer.model;

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
@Table(name="ch_daily")
public class Daily {
	private Integer id;
	private String title;
	private String summary;
	private String content;
	private Date updateTime;
	private Integer hasSee = new Integer(0);
	private User author;
	private User leader;
	
	public Daily() {
		super();
	}
	public Daily(Integer id, String title, String summary, String content, Date updateTime, Integer hasSee, String titleName) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.updateTime = updateTime;
		this.hasSee = hasSee;
		author = new User();
		Staff staff = new Staff();
		staff.setTitleName(titleName);
		author.setStaff(staff);
	}
	
	public Daily(Integer id, String title, String summary,String content, Date updateTime, Integer hasSee, Integer authorId, Integer leaderId) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.updateTime = updateTime;
		this.hasSee = hasSee;
		author = new User();
		author.setId(authorId);
		leader = new User();
		leader.setId(leaderId);
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
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="parent_id")
	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
	}
	
	@Lob
	@Column(length = 16777216)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
	
}
