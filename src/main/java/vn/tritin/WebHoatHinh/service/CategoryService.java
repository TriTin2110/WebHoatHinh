package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Category;

public interface CategoryService {
	public Category findByName(String name);

	public Category save(Category category);

	public Category merge(Category category);
}
