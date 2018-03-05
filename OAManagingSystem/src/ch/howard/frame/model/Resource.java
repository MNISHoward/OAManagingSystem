package ch.howard.frame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="ch_resource")
public class Resource {

	private Integer id;
	private String name;
	private String titleName;
	private String url;
	private Integer state;
	private List<Menu> menus = new ArrayList<Menu>();
	
	
	public Resource() {
		super();
	}
	public Resource(String name, String titleName, String url, Integer state) {
		super();
		this.name = name;
		this.titleName = titleName;
		this.url = url;
		this.state = state;
	}
	
	public Resource(Integer id, String name, String titleName, String url) {
		super();
		this.id = id;
		this.name = name;
		this.titleName = titleName;
		this.url = url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="state", columnDefinition="int default 0")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy="resource")
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	
}
