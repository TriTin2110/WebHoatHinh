package vn.tritin.WebHoatHinh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CategoryMenuComponent {
	@Id
	private int id;
	private String name;

	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public CategoryMenuComponent() {
	}

	public CategoryMenuComponent(int id, String name, Category category) {
		this.id = id;
		this.name = name;
		this.category = category;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
