package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Studio {
	@Id
	private String name;

	@OneToMany(mappedBy = "studio", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<Video> videos;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	public Studio() {
	}

	public Studio(String name) {
		this.name = name;
		this.videos = new ArrayList<Video>();
	}

	public Studio(String name, List<Video> videos, Country country) {
		this.name = name;
		this.videos = videos;
		this.country = country;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
