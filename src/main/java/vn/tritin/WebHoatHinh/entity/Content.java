package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content {
	@Id
	private String id;
	@Lob
	private String description;
	@Column(name = "date_uploaded")
	private Date dateUploaded;
	private String banner;

	public Content() {
	}

	public Content(String id) {
		this.id = id;
		this.dateUploaded = new Date(System.currentTimeMillis());
	}

	public Content(String id, String banner) {
		super();
		this.id = id;
		this.banner = banner;
	}

	public Content(String id, String description, Date dateUploaded, String banner) {
		this.id = id;
		this.description = description;
		this.dateUploaded = dateUploaded;
		this.banner = banner;
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

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String convertToString() {
		return new String(description);
	}

}
