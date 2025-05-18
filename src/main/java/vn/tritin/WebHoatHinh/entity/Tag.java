package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tag {
	@Id
	private String id;
	@Column(name = "date_created")
	private Date dateCreated;
	@ManyToMany(mappedBy = "tags")
	private List<News> news;

	public Tag() {
	}

	public Tag(String id, Date dateCreated, List<News> news) {
		this.id = id;
		this.dateCreated = dateCreated;
		this.news = news;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", dateCreated=" + dateCreated + "]";
	}

}
