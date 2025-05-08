package vn.tritin.WebHoatHinh.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	private String id;
	private String content;
	private Date dateComment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "video_detail_id")
	private VideoDetail videoDetail;

	public Comment() {

	}

	public Comment(String id, String content, Date dateComment, User user, VideoDetail videoDetail) {
		this.id = id;
		this.content = content;
		this.dateComment = dateComment;
		this.user = user;
		this.videoDetail = videoDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateComment() {
		return dateComment;
	}

	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VideoDetail getVideoDetail() {
		return videoDetail;
	}

	public void setVideoDetail(VideoDetail videoDetail) {
		this.videoDetail = videoDetail;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", dateComment=" + dateComment + ", user=" + user
				+ ", videoDetail=" + videoDetail + "]";
	}

}
