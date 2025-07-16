package vn.tritin.WebHoatHinh.entity;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Video extends Content {
	private String status, director, language, duration, quality;
	private int viewer;

	@OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
	private VideoTag videoTag;

	@OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
	private VideoDetail videoDetail;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "videos_categories", joinColumns = @JoinColumn(name = "video_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "studio_id")
	private Studio studio;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
	private VideoAnalyst videoAnalyst;

	public Video() {
	}

	public Video(String id, String banner, String status, String director, String language, String duration,
			String quality, int viewer) {
		super(id, banner);
		this.status = status;
		this.director = director;
		this.language = language;
		this.duration = duration;
		this.quality = quality;
		this.viewer = viewer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public int getViewer() {
		return viewer;
	}

	public void setViewer(int viewer) {
		this.viewer = viewer;
	}

	public VideoTag getVideoTag() {
		return videoTag;
	}

	public void setVideoTag(VideoTag videoTag) {
		this.videoTag = videoTag;
	}

	public VideoDetail getVideoDetail() {
		return videoDetail;
	}

	public void setVideoDetail(VideoDetail videoDetail) {
		this.videoDetail = videoDetail;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Studio getStudio() {
		return studio;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public VideoAnalyst getVideoAnalyst() {
		return videoAnalyst;
	}

	public void setVideoAnalyst(VideoAnalyst videoAnalyst) {
		this.videoAnalyst = videoAnalyst;
	}

	@Override
	public String toString() {
		return "Video [id =" + getId() + "categories="
				+ categories.stream().map(o -> o.getName()).collect(Collectors.joining(",")).toString() + ", studio="
				+ studio.getName() + ", country=" + country.getName() + ", viewer" + getViewer() + "]";
	}

}
