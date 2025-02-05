package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Category;

public interface CategoryService {
	public Category findByName(String name);

	public void save(Category category);

	public void merge(Category category);
}
