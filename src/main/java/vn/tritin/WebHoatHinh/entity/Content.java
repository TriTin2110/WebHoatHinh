package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content {
	@Id
	private String id;
	private byte[] description;
	@Column(name = "date_uploaded")
	private Date dateUploaded;
	@Column(name = "byte_length_description_after_zip")
	private int byteLengthDescriptionAfterZip;
	private String banner;

	public Content() {
	}

	public Content(String id) {
		this.id = id;
	}

	public Content(String id, String banner) {
		super();
		this.id = id;
		this.banner = banner;
	}

	public Content(String id, byte[] description, Date dateUploaded, int byteLengthDescriptionAfterZip, String banner) {
		this.id = id;
		this.description = description;
		this.dateUploaded = dateUploaded;
		this.byteLengthDescriptionAfterZip = byteLengthDescriptionAfterZip;
		this.banner = banner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getDescription() {
		return description;
	}

	public void setDescription(byte[] description) {
		this.description = description;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public int getByteLengthDescriptionAfterZip() {
		return byteLengthDescriptionAfterZip;
	}

	public void setByteLengthDescriptionAfterZip(int byteLengthDescriptionAfterZip) {
		this.byteLengthDescriptionAfterZip = byteLengthDescriptionAfterZip;
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
