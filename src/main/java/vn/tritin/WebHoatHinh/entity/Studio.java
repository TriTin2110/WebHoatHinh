package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Studio extends OutDoor {

	@OneToMany(mappedBy = "studio", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<Video> videos;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	public Studio() {
	}

	public Studio(String name) {
		super(name, 0);
		this.videos = new ArrayList<Video>();
	}

	public Studio(String name, int totalMovie, List<Video> videos, Country country) {
		super(name, totalMovie);
		this.videos = videos;
		this.country = country;
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
