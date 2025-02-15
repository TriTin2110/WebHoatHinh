package vn.tritin.WebHoatHinh.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Category {
	@Id
	private String name;

	@OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
	private CategoryMenuComponent categoryMenuComponent;

	@ManyToMany
	@JoinTable(name = "videos_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "video_id"))
	private List<Video> videos;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
		this.videos = new ArrayList<Video>();
	}

	public Category(String name, List<Video> videos) {
		this.name = name;
		this.videos = videos;
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

	public CategoryMenuComponent getCategoryMenuComponent() {
		return categoryMenuComponent;
	}

	public void setCategoryMenuComponent(CategoryMenuComponent categoryMenuComponent) {
		this.categoryMenuComponent = categoryMenuComponent;
	}

}
