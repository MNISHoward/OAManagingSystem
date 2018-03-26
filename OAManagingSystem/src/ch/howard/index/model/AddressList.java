package ch.howard.index.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ch.howard.frame.model.User;

@Entity
@Table(name="ch_addresslist")
public class AddressList {
	private Integer id;
	private String name;
	private Integer sex;
	/**
	 * 0私人联系人
	 * 1公司
	 * 2竞争对手
	 */
	private Integer type;
	private String phone;
	private Date addTime;
	private String comment;
	private String address;
	private String company;
	private User user;
	
	
	public AddressList() {
		super();
	}
	public AddressList(Integer id, String name, Integer sex, Integer type, String phone, Date addTime, String comment,
			String address, String company) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.type = type;
		this.phone = phone;
		this.addTime = addTime;
		this.comment = comment;
		this.address = address;
		this.company = company;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	
}
