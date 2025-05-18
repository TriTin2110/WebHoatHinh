package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String findNewsById(@PathVariable("id") String id, Model model) {
		System.out.println(id);
		News news = null;
		for (News newsItem : newsList) {
			if (id.equals(newsItem.getId())) {
				news = newsItem;
				break;
			}
		}
		model.addAttribute("news", news);
		System.out.println("Da chuyen huong");
		return "news";
	}
}
