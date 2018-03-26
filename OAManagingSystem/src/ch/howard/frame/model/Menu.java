package ch.howard.frame.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ch.howard.rbac.model.Role;

@Entity
@Table(name="ch_menu")
public class Menu {
	private Integer id;
	private String name;
	private String titleName;
	private String url;
	/**
	 * 0正常
	 * 1非正常
	 */
	private Integer state;
	private Resource resource;
	private Set<Role> roles = new HashSet<Role>();
	private String iconClass;
	
	
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public Menu() {
		super();
	}
	public Menu(String name, String titleName, String url, Integer state, Resource resource) {
		super();
		this.name = name;
		this.titleName = titleName;
		this.url = url;
		this.state = state;
		this.resource = resource;
	}
	
	public Menu(Integer id, Integer state,String titleName, String url) {
		super();
		this.id = id;
		this.state = state;
		this.titleName = titleName;
		this.url = url;
	}
	
	public Menu(Integer id, Integer state, String name, String titleName, String url,  Integer resourceId,
			String iconClass) {
		super();
		this.id = id;
		this.name = name;
		this.titleName = titleName;
		this.url = url;
		this.state = state;
		this.resource = new Resource();
		this.resource.setId(resourceId);
		this.iconClass = iconClass;
	}
	public Menu(Integer id) {
		super();
		this.id = id;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitleName() {
		return titleName;
	}
	
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	@Column(name="state", columnDefinition="int default 0")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="resource_id")
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	@Fetch(FetchMode.JOIN)
	@ManyToMany(mappedBy="menus")
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role r) {
		this.roles.add(r);
	}
	
	public void removeRole(Role r) {
		this.roles.remove(r);
	}
	
}
