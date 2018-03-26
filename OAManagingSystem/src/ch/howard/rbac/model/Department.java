package ch.howard.rbac.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private Department department;
	private List<Department> departments = new ArrayList<Department>();
	private List<Staff> staffs = new ArrayList<Staff>();
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="dept_id")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy="department")
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	public Department() {
		super();
	}
	public Department(String name) {
		super();
		this.name = name;
	}
	
	public Department(String name, Department department, List<Department> departments) {
		super();
		this.name = name;
		this.department = department;
		this.departments = departments;
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
	
	@OneToMany(mappedBy="department",fetch=FetchType.LAZY)
	public List<Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}

	
}
