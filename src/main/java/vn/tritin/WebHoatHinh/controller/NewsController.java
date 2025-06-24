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
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsNotExistsException;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
@RequestMapping("/movie-news")
public class NewsController {
	private NewsService service;
	private VideoService videoSer;

	@Autowired
	public NewsController(NewsService service, VideoService videoSer) {
		this.service = service;
		this.videoSer = videoSer;
	}

	@GetMapping("/{id}")
	public String findNewsById(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		List<News> newsList = service.findAll();
		Account account = (Account) request.getSession().getAttribute("account");
		News news = null;
		String newsItemId = null;
		for (News newsItem : newsList) {
			newsItemId = newsItem.getId();
			if (id.equals(newsItemId)) {
				news = newsItem;
				break;
			}
		}
		if (news == null)
			throw new NewsNotExistsException();

		model.addAttribute("account", account);
		model.addAttribute("news", news);
		model.addAttribute("currentPage", "news");
		return "news";
	}

	@GetMapping("/show-news")
	public String showNewsList(Model model) {
		List<News> newsList = service.findAll();
		List<Video> videos = videoSer.findAll();
		List<Video> videoList = videoSer.getVideosByAmount(videos, 4);
		model.addAttribute("news", newsList);
		model.addAttribute("videos", videoList);
		model.addAttribute("currentPage", "news");
		return "other/news/news-page";
	}
}
