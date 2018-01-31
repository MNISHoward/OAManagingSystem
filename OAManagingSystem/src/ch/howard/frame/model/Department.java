package ch.howard.frame.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
@Entity
@Table(name="ch_dept")
public class Department {
	private Integer id;
	private String name;
	List<Staff> staffs;
	
	public Department() {
		super();
	}
	public Department(String name) {
		super();
		this.name = name;
	}
	public Department(String name, List<Staff> staffs) {
		super();
		this.name = name;
		this.staffs = staffs;
	}
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
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
	
	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy="department")
	public List<Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}
	
	
}
