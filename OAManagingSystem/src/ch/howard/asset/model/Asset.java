package ch.howard.asset.model;

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
@Entity
@Table(name="ch_asset")
public class Asset {
	private Integer id;
	private String titleName;
	private String model;
	private Date addTime;
	private AssetType assetType;
	private String phonenumber;
	private Double price;
	private String company;
	private String repairPhone;
	private String invoices;
	/**
	 * 0 正常
	 * 1分配
	 * 2删除
	 */
	private Integer state;
	
	
	
	public Asset() {
		super();
	}
	public Asset(Integer id) {
		super();
		this.id = id;
	}


	public Asset(Integer id, String titleName, String model, Date addTime, AssetType assetType, String phonenumber,
			Double price, String company, String repairPhone, String invoices, Integer state) {
		super();
		this.id = id;
		this.titleName = titleName;
		this.model = model;
		this.addTime = addTime;
		this.assetType = assetType;
		this.phonenumber = phonenumber;
		this.price = price;
		this.company = company;
		this.repairPhone = repairPhone;
		this.invoices = invoices;
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
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRepairPhone() {
		return repairPhone;
	}
	public void setRepairPhone(String repairPhone) {
		this.repairPhone = repairPhone;
	}
	public String getInvoices() {
		return invoices;
	}
	public void setInvoices(String invoices) {
		this.invoices = invoices;
	}
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name="assetType_id")
	public AssetType getAssetType() {
		return assetType;
	}
	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
