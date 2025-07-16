package vn.tritin.WebHoatHinh.exceptions.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsExistsException;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsNotExistsException;
import vn.tritin.WebHoatHinh.service.NewsService;

@ControllerAdvice
public class NewsExceptionHandler {
	private NewsService service;

	@Autowired
	public NewsExceptionHandler(NewsService service) {
		this.service = service;
	}

	@ExceptionHandler(exception = { NewsExistsException.class })
	public ResponseEntity<Map<String, String>> handlingNewsExistsException(NewsExistsException ex, Model model) {
		List<News> news = service.findAll();
		model.addAttribute("error", ex.getMessage());
		model.addAttribute("news", news);
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "false");
		map.put("message", ex.getMessage());
		return ResponseEntity.status(200).body(map);
	}

	@ExceptionHandler(exception = { NewsNotExistsException.class })
	public ResponseEntity<Map<String, String>> handlingNewsNotExistsException(NewsNotExistsException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "false");
		map.put("message", ex.getMessage());
		return ResponseEntity.status(200).body(map);
	}
}
