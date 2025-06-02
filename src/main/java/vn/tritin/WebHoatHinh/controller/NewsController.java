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

@Controller
@RequestMapping("/movie-news")
public class NewsController {
	private List<News> newsList;

	@Autowired
	public NewsController(List<News> newsList) {
		this.newsList = newsList;
	}

	@GetMapping("/{id}")
	public String findNewsById(@PathVariable("id") String id, Model model, HttpServletRequest request) {
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

		model.addAttribute("account", account);
		model.addAttribute("news", news);
		return "news";
	}
}
