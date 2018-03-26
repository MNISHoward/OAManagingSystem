package ch.howard.frame.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ch.howard.rbac.model.Role;
import ch.howard.rbac.model.Staff;

@Entity
@Table(name="ch_user")
public class User {
	private Integer id;
	private String username;
	private String password;
	private Date loginTime;
	/**
	 * 0正常
	 * 1锁定
	 */
	private Integer state;
	private Staff staff;
	private Set<Role> roles = new HashSet<Role>(); 
	
	@Fetch(FetchMode.JOIN)
	@ManyToMany
	@JoinTable(
		name="user_role",
		joinColumns = {
			@JoinColumn(name="user_id")
		},
		inverseJoinColumns = {
			@JoinColumn(name="role_id")
	})
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

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
	
	public User(Set<Role> roles,Staff staff) {
		super();
		this.staff = staff;
		this.roles = roles;
	}
	
	public User(Integer id, String username, Integer state) {
		super();
		this.id = id;
		this.username = username;
		this.state = state;
	}
	
	public User(Integer id) {
		super();
		this.id = id;
	}
	
	public User(Integer id, Integer staffId,String name) {
		super();
		this.id = id;
		this.staff = new Staff();
		this.staff.setId(staffId);
		this.staff.setTitleName(name);
	}
	
	

	public User(Integer id, String titleName, String email) {
		super();
		this.id = id;
		this.staff = new Staff();
		staff.setTitleName(titleName);
		staff.setEmail(email);
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
	
	public void addRole(Role r) {
		this.roles.add(r);
	}
	
	public void removeRole(Role r) {
		this.roles.remove(r);
	}
}
