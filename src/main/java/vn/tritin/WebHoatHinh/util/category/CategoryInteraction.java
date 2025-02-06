package vn.tritin.WebHoatHinh.util.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.service.CategoryService;

@Component
public class CategoryInteraction {
	private CategoryService categorySer;

	@Autowired
	public CategoryInteraction(CategoryService categorySer) {
		this.categorySer = categorySer;
	}

	public Category findCategories(String name) {
		Category category = categorySer.findByName(name);
		if (category == null) {
			category = new Category(name);
			category = categorySer.save(category);
		}
		return category;
	}

	public Category update(Category category) {
		return categorySer.merge(category);
	}

}
