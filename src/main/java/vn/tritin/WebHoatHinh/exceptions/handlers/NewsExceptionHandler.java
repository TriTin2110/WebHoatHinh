package vn.tritin.WebHoatHinh.exceptions.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsExistsException;
import vn.tritin.WebHoatHinh.service.NewsService;

@ControllerAdvice
public class NewsExceptionHandler {
	private NewsService service;

	@Autowired
	public NewsExceptionHandler(NewsService service) {
		this.service = service;
	}

	@ExceptionHandler(exception = { NewsExistsException.class })
	public String handlingNewsExistsException(NewsExistsException ex, Model model) {
		List<News> news = service.findAll();
		model.addAttribute("error", ex.getMessage());
		model.addAttribute("news", news);
		return "manage/news/news-list";
	}
}
