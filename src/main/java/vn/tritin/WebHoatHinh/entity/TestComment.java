package vn.tritin.WebHoatHinh.entity;

import java.util.Date;

//Testing comment class
public class TestComment {
	private String user, content;
	private Date date;

	public TestComment() {
	}

	public TestComment(String user, String content, Date date) {
		this.user = user;
		this.content = content;
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "TestComment [user=" + user + ", content=" + content + ", date=" + date + "]";
	}

}
