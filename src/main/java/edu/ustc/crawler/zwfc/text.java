package edu.ustc.crawler.zwfc;

public class text {

	public int id;
	//public static
	public String content;

	public String weburl;

	public text(int username) {
		this.id = username;
	};

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}

	public String getweburl() {
		return weburl;
	}

	public void setweburl(String weburl) {
		this.weburl = weburl;
	}

}
