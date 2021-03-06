package ch.howard.rbac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Table(name="ch_staff")
@Entity
public class Staff {
	
	private Integer id;
	private String name;
	private String titleName;
	/**
	 * 0男
	 * 1女
	 */
	private Integer sex;
	private Date birthday;
	private String email;
	private String phone;
	private String address;
	private Double salary;
	private String job;
	private Department department;
	private Integer hasUser = new Integer(0);
	
	public Staff() {
	}
	public Staff(Integer id) {
		super();
		this.id = id;
	}
	public Staff(Integer id, String name, String titleName, Integer sex, Date birthday, String email, String phone,
			String address, Double salary, String job, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.titleName = titleName;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.salary = salary;
		this.job = job;
		this.department = department;
	}

	public Staff(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="dept_id")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Column(name="has_user")
	public Integer getHasUser() {
		return hasUser;
	}

	public void setHasUser(Integer hasUser) {
		this.hasUser = hasUser;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", titleName=" + titleName + ", sex=" + sex + ", birthday="
				+ birthday + ", email=" + email + ", phone=" + phone + ", address=" + address + ", salary=" + salary
				+ ", job=" + job + ", department=" + department + ", hasUser=" + hasUser + "]";
	}
	
}
