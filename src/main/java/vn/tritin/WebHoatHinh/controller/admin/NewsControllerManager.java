package vn.tritin.WebHoatHinh.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.exceptions.FileNotFoundException;
import vn.tritin.WebHoatHinh.model.NewsCreator;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.TagService;
import vn.tritin.WebHoatHinh.service.util.FileService;

@Controller
@RequestMapping("/admin/news")
public class NewsControllerManager {
	private NewsService newsSer;

	@Autowired
	public NewsControllerManager(NewsService newsSer, TagService tagSer, FileService fileSer) {
		this.newsSer = newsSer;
		// We will need FileService and TagService for method save news of NewsService
		// so here I will set both this variable to NewsService
		this.newsSer.setFileSer(fileSer);
		this.newsSer.setTagSer(tagSer);
	}

	@GetMapping("")
	public String findAll(Model model) {
		List<News> news = newsSer.findAll();
		model.addAttribute("news", news);
		return "manage/news/news-list";
	}

	@GetMapping("/show-create")
	public String showCreatePage(Model model) {
		NewsCreator creator = new NewsCreator();
		model.addAttribute("creator", creator);
		return "manage/news/news-create";
	}

	@PostMapping("")
	public String insert(@ModelAttribute("news") NewsCreator newsCreator, @RequestParam("banner") MultipartFile file) {
		if (file.isEmpty())
			throw new FileNotFoundException();
		else
			newsSer.save(newsCreator, file);
		return "redirect:/admin/news";
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		newsSer.remove(id);
	}

}
