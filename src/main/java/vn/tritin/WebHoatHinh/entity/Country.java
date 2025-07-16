package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Country extends OutDoor {

	@OneToMany(mappedBy = "country")
	private List<Video> videos;

	@OneToMany(mappedBy = "country")
	private List<Studio> studios;

	public Country() {
	}

	public Country(String name) {
		super(name, 0);
		this.videos = new ArrayList<Video>();
		this.studios = new ArrayList<Studio>();
	}

	public Country(String name, int totalMovie, List<Video> videos, List<Studio> studios) {
		super(name, totalMovie);
		this.videos = videos;
		this.studios = studios;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Studio> getStudios() {
		return studios;
	}

	public void setStudios(List<Studio> studios) {
		this.studios = studios;
	}

}
