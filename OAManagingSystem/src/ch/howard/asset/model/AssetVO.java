package ch.howard.asset.model;

public class AssetVO {
	
	private String name;
	private Integer lendQuantity;
	private Integer surplusQuantity;
	private Integer totalQuantity;
	
	public AssetVO() {
		super();
	}
	public AssetVO(String name, Integer lendQuantity, Integer surplusQuantity, Integer totalQuantity) {
		super();
		this.name = name;
		this.lendQuantity = lendQuantity;
		this.surplusQuantity = surplusQuantity;
		this.totalQuantity = totalQuantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLendQuantity() {
		return lendQuantity;
	}
	public void setLendQuantity(Integer lendQuantity) {
		this.lendQuantity = lendQuantity;
	}
	public Integer getSurplusQuantity() {
		return surplusQuantity;
	}
	public void setSurplusQuantity(Integer surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	
}
