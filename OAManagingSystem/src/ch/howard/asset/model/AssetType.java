package ch.howard.asset.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ch_assettype")
public class AssetType {
	private Integer id;
	private String name;
	private String titleName;
	private Date updateTime;
	
	public AssetType() {
		super();
	}
	public AssetType(Integer id) {
		super();
		this.id = id;
	}
	public AssetType(Integer id, String name, String titleName, Date updateTime) {
		super();
		this.id = id;
		this.name = name;
		this.titleName = titleName;
		this.updateTime = updateTime;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
