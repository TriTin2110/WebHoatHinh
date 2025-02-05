package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@OneToMany(mappedBy = "country", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<Video> videos;

	@OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}

}
