package ch.howard.rbac.model;

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
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ch.howard.frame.model.User;

@Entity
@Table(name="ch_role")
public class Role {
	private Integer id;
	private String name;
	private String titleName;
	private Integer state;
	private Set<Menu> menus = new HashSet<Menu>();
	private Set<User> users = new HashSet<User>();
	
	public Role() {
		super();
	}
	public Role(String name, String titleName, Integer state) {
		super();
		this.name = name;
		this.titleName = titleName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public Integer getState() {
		return state;
	}
	
	@Column(name="state", columnDefinition="int default 0")
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Fetch(FetchMode.JOIN)
	@ManyToMany
	@JoinTable(
			name="role_menu",
			joinColumns = {
				@JoinColumn(name="role_id")
			},
			inverseJoinColumns = {
				@JoinColumn(name="menu_id")
			})
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	@Fetch(FetchMode.JOIN)
	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	} 
	
	public void addUser(User u) {
		this.users.add(u);
	}
	
	public void addMenu(Menu m) {
		this.menus.add(m);
	}
	
}
