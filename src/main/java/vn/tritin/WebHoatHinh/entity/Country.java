package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Country {
	@Id
	private String name;

	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private List<Video> videos;

	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private List<Studio> studios;

	public Country() {
	}

	public Country(String name) {
		this.name = name;
		this.videos = new ArrayList<Video>();
		this.studios = new ArrayList<Studio>();
	}

	public Country(String name, List<Video> videos, List<Studio> studios) {
		this.name = name;
		this.videos = videos;
		this.studios = studios;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
