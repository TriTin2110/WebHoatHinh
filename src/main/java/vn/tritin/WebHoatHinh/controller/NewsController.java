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
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsNotExistsException;
import vn.tritin.WebHoatHinh.service.NewsService;

@Controller
@RequestMapping("/movie-news")
public class NewsController {
	private List<News> newsList;
	private NewsService service;

	@Autowired
	public NewsController(NewsService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public String findNewsById(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		this.newsList = service.findAll();
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
		return "news";
	}
}
