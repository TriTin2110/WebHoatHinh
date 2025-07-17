package vn.tritin.WebHoatHinh.model;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewsCreator {
	@NotBlank(message = "Không được để trống tiêu đề")
	private String id;
	@NotBlank(message = "Không được để trống mô tả")
	@Size(min = 20, max = 3000, message = "Độ dài của mô tả không hợp lệ")
	private String description;
	private Date dateUploaded;
	@NotBlank(message = "Không được để trống tên tác giả")
	private String authorName;
	private String tags;
	private String banner;

	public NewsCreator() {
	}

	public NewsCreator(String id, String description, String authorName, String tags, String banner) {
		this.id = id;
		this.description = description;
		this.authorName = authorName;
		this.tags = tags;
		this.banner = banner;
	}

	public NewsCreator(String id, String description, Date dateUploaded, String authorName, String tags) {
		this.id = id;
		this.description = description;
		this.dateUploaded = dateUploaded;
		this.authorName = authorName;
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Override
	public String toString() {
		return "NewsCreator [id=" + id + ", description=" + description + ", dateUploaded=" + dateUploaded
				+ ", authorName=" + authorName + ", tags=" + tags + "]";
	}

}
