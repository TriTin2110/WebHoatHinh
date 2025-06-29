package vn.tritin.WebHoatHinh.model;

public class VectorStoreDTO {
	private String id, categories, director, language, description;
	private int viewer;

	public VectorStoreDTO(String id, String categories, String director, String language, String description,
			int viewer) {
		this.id = id;
		this.categories = categories;
		this.director = director;
		this.language = language;
		this.description = description;
		this.viewer = viewer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getViewer() {
		return viewer;
	}

	public void setViewer(int viewer) {
		this.viewer = viewer;
	}

	@Override
	public String toString() {
		return id + ";" + categories + ";" + director + ";" + language + ";" + description + ";" + viewer;
	}

}
