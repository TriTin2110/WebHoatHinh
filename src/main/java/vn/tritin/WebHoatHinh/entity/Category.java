package vn.tritin.WebHoatHinh.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Category extends OutDoor {

	@ManyToMany
	@JoinTable(name = "videos_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "video_id"))
	private List<Video> videos;

	public Category() {
	}

	public Category(String name) {
		super(name, 0);
	}

	public Category(String name, int totalMovie, List<Video> videos) {
		super(name, totalMovie);
		this.videos = videos;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
}
