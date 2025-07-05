package vn.tritin.WebHoatHinh.util.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.service.CategoryService;

@Component
public class CategoryInteraction {
	private CategoryService categoryService;

	@Autowired
	public CategoryInteraction(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public Category findCategories(String name) {
		Category category = categoryService.findByName(name);
		if (category == null) {
			category = new Category(name);
			category = categoryService.save(category);
		}
		return category;
	}

	public Category update(Category category) {
		return categoryService.merge(category);
	}

	public Model setModelCategory(Model model) {
		List<Category> categories = categoryService.findAll();
		String dto = categories.stream().map(o -> o.getName()).collect(Collectors.joining(","));
		model.addAttribute("categories", dto);
		return model;
	}
}
