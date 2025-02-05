package vn.tritin.WebHoatHinh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class VideoDetail {
	@Id
	private String id;
	private String path;
	@OneToOne
	@JoinColumn(name = "video_id")
	private Video video;

	public VideoDetail() {
	}

	public VideoDetail(String id, String path, Video video) {
		super();
		this.id = id;
		this.path = path;
		this.video = video;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
