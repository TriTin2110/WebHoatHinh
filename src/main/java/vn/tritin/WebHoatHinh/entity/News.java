package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class News extends Content {
	@Column(name = "author_name")
	private String authorName;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "news_tags", joinColumns = @JoinColumn(name = "news_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	public News() {
	}

	public News(String id) {
		super(id);
	}

	public News(String id, String description, Date dateUploaded, String authorName, String banner) {
		super(id, description, dateUploaded, banner);
		this.authorName = authorName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "News [authorName=" + authorName + ", getId()=" + getId() + ", getDescription()=" + convertToString()
				+ ", getDateUploaded()=" + getDateUploaded() + ", getBanner()=" + getBanner() + "]";
	}

}
