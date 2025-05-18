package vn.tritin.WebHoatHinh.service;

import java.util.List;

import vn.tritin.WebHoatHinh.entity.News;

public interface NewsService {
	public News save(News news);

	public News findById(String id);

	public List<News> findAll();
}
