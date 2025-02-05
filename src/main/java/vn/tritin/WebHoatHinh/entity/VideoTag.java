package vn.tritin.WebHoatHinh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class VideoTag {
	@Id
	private String id;
	private String name, avatar, typeOfVideo, duration;
	private int viewer;
	@OneToOne
	@JoinColumn(name = "video_id")
	private Video video;

	public VideoTag() {
	}

	public VideoTag(String id, String name, String avatar, String typeOfVideo, String duration, int viewer,
			Video video) {
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.typeOfVideo = typeOfVideo;
		this.duration = duration;
		this.viewer = viewer;
		this.video = video;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTypeOfVideo() {
		return typeOfVideo;
	}

	public void setTypeOfVideo(String typeOfVideo) {
		this.typeOfVideo = typeOfVideo;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getViewer() {
		return viewer;
	}

	public void setViewer(int viewer) {
		this.viewer = viewer;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
