package vn.tritin.WebHoatHinh.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOCategory;
import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private DAOCategory dao;

	@Autowired
	public CategoryServiceImpl(DAOCategory dao) {
		this.dao = dao;
	}

	@Override
	public Category findByName(String name) {
		// TODO Auto-generated method stub
		return dao.findByName(name);
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return dao.save(category);
	}

	@Override
	public Category merge(Category category) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(category);
	}

}
