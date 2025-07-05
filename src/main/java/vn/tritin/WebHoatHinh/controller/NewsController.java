package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.entity.Tag;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsNotExistsException;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.util.StringHandler;
import vn.tritin.WebHoatHinh.util.category.CategoryInteraction;

@Controller
@RequestMapping("/movie-news")
public class NewsController {
	private NewsService service;
	private VideoService videoSer;
	private CategoryInteraction categoryInteraction;

	@Autowired
	public NewsController(NewsService service, VideoService videoSer, CategoryInteraction categoryInteraction) {
		this.service = service;
		this.videoSer = videoSer;
		this.categoryInteraction = categoryInteraction;
	}

	@GetMapping("/{id}")
	public String findNewsById(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		News news = service.getNewsAndTags(id);
		if (news == null)
			throw new NewsNotExistsException();
		StringHandler stringHandler = new StringHandler();
		news.setDescription(stringHandler.decrypt(news.getDescription()));
		List<Tag> tags = news.getTags();

		model = categoryInteraction.setModelCategory(model);
		model.addAttribute("account", account);
		model.addAttribute("news", news);
		model.addAttribute("tags", tags);
		model.addAttribute("currentPage", "news");
		return "news";
	}

	@GetMapping("/show-news")
	public String showNewsList(Model model, HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		List<News> newsList = service.findAll();
		List<Video> videos = videoSer.findAll();
		List<Video> videoList = videoSer.getVideosByAmount(videos, 4);
		model = categoryInteraction.setModelCategory(model);
		model.addAttribute("news", newsList);
		model.addAttribute("videos", videoList);
		model.addAttribute("currentPage", "news");
		model.addAttribute("account", account);
		return "other/news/news-page";
	}

}
