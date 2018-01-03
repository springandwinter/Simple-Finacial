package org.loader.labelview;

public class NewsItem {
	private int icon;
	private String title;
	private String content;
	
	public NewsItem(int icon, String title, String content) {
		super();
		this.icon = icon;
		this.title = title;
		this.content = content;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
