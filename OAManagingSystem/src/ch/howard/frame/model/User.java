package ch.howard.frame.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="ch_user")
public class User {
	private Integer id;
	private String username;
	private String password;
	private Date loginTime;
	private Integer state;
	private Staff staff;
	
	
	public User() {
		super();
	}
	
	public User(String username, String password, Date loginTime, Integer state, Staff staff) {
		super();
		this.username = username;
		this.password = password;
		this.loginTime = loginTime;
		this.state = state;
		this.staff = staff;
	}


	@Column(name="state", columnDefinition="int default 0")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	@Fetch(FetchMode.JOIN)
	@OneToOne
	@JoinColumn(name="staff_id")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	
}
