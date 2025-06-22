package vn.tritin.WebHoatHinh.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.model.NewsCreator;
import vn.tritin.WebHoatHinh.service.util.FileService;

public interface NewsService {
	public News saveAndFlush(News news);

	public News findById(String id);

	public List<News> findAll();

	public boolean remove(String id);

	public void setTagSer(TagService tagSer);

	public void setFileSer(FileService fileSer);

	public List<News> updateListNews();

	public News prepareData(NewsCreator newsCreator, MultipartFile file);

	public News update(News news);

	public List<News> getNewestNews(int amount);
}
