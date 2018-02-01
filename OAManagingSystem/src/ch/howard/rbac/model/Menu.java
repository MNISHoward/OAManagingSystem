package ch.howard.rbac.model;

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

@Entity
@Table(name="ch_menu")
public class Menu {
	private Integer id;
	private String name;
	private String titleName;
	private String url;
	private Integer state;
	private Resource resource;
	private Set<Role> roles;
	
	
	
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
	
	
	
}
