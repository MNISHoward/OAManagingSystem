package ch.howard.index.model;

public class DailyModelDriven {
	private Integer id;
	private String title;
	private String summary;
	private String myContent;
	private String leader;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMyContent() {
		return myContent;
	}
	public void setMyContent(String myContent) {
		this.myContent = myContent;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	@Override
	public String toString() {
		return "DailyModelDriven [id=" + id + ", title=" + title + ", summary=" + summary + ", myContent=" + myContent
				+ ", leader=" + leader + "]";
	}
	
	
}
