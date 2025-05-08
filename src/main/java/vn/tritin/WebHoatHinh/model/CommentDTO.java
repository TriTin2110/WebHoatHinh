package vn.tritin.WebHoatHinh.model;

import java.util.Date;

public class CommentDTO {
	private String id, accountId, text, videoDetailId;
	private Date date;

	public CommentDTO() {
	}

	public CommentDTO(String id, String accountId, String text, String videoDetailId, Date date) {
		this.id = id;
		this.accountId = accountId;
		this.text = text;
		this.videoDetailId = videoDetailId;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVideoDetailId() {
		return videoDetailId;
	}

	public void setVideoDetailId(String videoDetailId) {
		this.videoDetailId = videoDetailId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", accountId=" + accountId + ", text=" + text + ", videoDetailId="
				+ videoDetailId + ", date=" + date + "]";
	}

}
