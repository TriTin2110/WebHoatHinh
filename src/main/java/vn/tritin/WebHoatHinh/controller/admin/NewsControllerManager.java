package vn.tritin.WebHoatHinh.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.entity.Tag;
import vn.tritin.WebHoatHinh.exceptions.exceptions.FileNotFoundException;
import vn.tritin.WebHoatHinh.exceptions.exceptions.NewsNotExistsException;
import vn.tritin.WebHoatHinh.model.NewsCreator;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.TagService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.StringHandler;

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
	public String saveAndUpdate(@ModelAttribute("news") NewsCreator newsCreator,
			@RequestParam("banner") MultipartFile file, @RequestParam("present-id") String previousId) {
		if (file.isEmpty())
			throw new FileNotFoundException();
		else {
			News news = newsSer.findById(newsCreator.getId());
			if (previousId.isEmpty()) { // Insert signal
				news = newsSer.prepareData(newsCreator, file);
				news = newsSer.save(news);
			} else {
				News previousNews = newsSer.findById(previousId);
				if (previousNews == null)
					throw new NewsNotExistsException();
				if (previousNews != null
						&& news == null) { /*- When news's previous Id different from news's id inputed by user*/
					newsSer.remove(previousId);
				}
				news = newsSer.prepareData(newsCreator, file);
				newsSer.update(news);
			}

			newsSer.updateListNews();
		}
		return "redirect:/admin/news";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		newsSer.remove(id);
		newsSer.updateListNews();
		return "redirect:/admin/news";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		News news = newsSer.findById(id);
		StringHandler stringHandler = new StringHandler();
		if (news == null)
			throw new NewsNotExistsException();
		StringBuilder tags = new StringBuilder();
		List<Tag> tagList = news.getTags();
		String description = stringHandler.decrypt(news.getDescription());
		for (Tag tag : tagList) {
			tags.append(tag.getId());
			tags.append(",");
		}

		NewsCreator creator = new NewsCreator(news.getId(), description, news.getAuthorName(), tags.toString());
		model.addAttribute("creator", creator);
		model.addAttribute("presentId", creator.getId()); // Each time showing update form we will attach previous
															// news's id for checking when updating
		return "manage/news/news-create";
	}
}
