package vn.tritin.WebHoatHinh.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.service.CategoryService;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.VideoService;

//This Setup class will load all videos, categories from the DB when the application begin

@Configuration
public class Setup implements WebMvcConfigurer {
	private VideoService videoService;
	private CategoryService categoryService;
	private NewsService newsService;

	@Autowired
	public Setup(VideoService videoService, CategoryService categoryService, NewsService newsService) {
		this.videoService = videoService;
		this.categoryService = categoryService;
		this.newsService = newsService;
	}

	/*-
	 * When the application running it will load all videos from db
	 * Then save to cache
	 * */
//	@Bean
//	public List<Video> getAllVideo() {
//		return videoService.findAll();
//	}

	@Bean
	public List<Category> getAllCategory() {
		return categoryService.findAll();
	}

	@Bean
	public List<News> getAllNews() {
		List<News> news = newsService.findAll();
		return news;
	}
}
