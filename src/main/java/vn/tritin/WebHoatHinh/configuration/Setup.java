package vn.tritin.WebHoatHinh.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.CategoryService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Configuration
public class Setup {
	private VideoService videoService;
	private CategoryService categoryService;
	private List<String> codesEmail;

	@Autowired
	public Setup(VideoService videoService, CategoryService categoryService) {
		this.videoService = videoService;
		this.categoryService = categoryService;
		this.codesEmail = new ArrayList<String>();
	}

	@Bean
	public List<Video> getAllVideo() {
		return videoService.findAll();
	}

	@Bean
	public List<Category> getAllCategory() {
		return categoryService.findAll();
	}

	@Bean
	public List<String> getAllEmail() {
		return this.codesEmail;
	}
}
