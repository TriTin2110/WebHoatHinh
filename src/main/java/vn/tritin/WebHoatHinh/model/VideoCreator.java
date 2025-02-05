package vn.tritin.WebHoatHinh.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VideoCreator {
	@NotBlank(message = "Không được để trống ID!")
	@Size(min = 7, message = "Độ dài tối thiểu là 6 ký tự!")
	private String id;

	@NotBlank(message = "Không được để trống tên!")
	@Size(min = 3, message = "Độ dài tối thiểu là 6 ký tự!")
	private String name;

	private String avatar, director, language, country, categories, studio;

	public VideoCreator() {
	}

	public VideoCreator(
			@NotBlank(message = "Không được để trống ID!") @Size(min = 7, message = "Độ dài tối thiểu là 6 ký tự!") String id,
			@NotBlank(message = "Không được để trống tên!") @Size(min = 3, message = "Độ dài tối thiểu là 6 ký tự!") String name,
			String avatar, String director, String language, String country, String categories, String studio) {
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.director = director;
		this.language = language;
		this.country = country;
		this.categories = categories;
		this.studio = studio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

}
