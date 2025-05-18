package vn.tritin.WebHoatHinh.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.entity.Tag;
import vn.tritin.WebHoatHinh.model.NewsCreator;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.TagService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.StringHandler;

@Controller
@RequestMapping("/admin/news")
public class NewsControllerManager {
	private NewsService newsSer;
	private TagService tagSer;
	private FileService fileSer;

	@Value("${path.news-banner}")
	private String bannerPath;

	@Autowired
	public NewsControllerManager(NewsService newsSer, TagService tagSer, FileService fileSer) {
		this.newsSer = newsSer;
		this.tagSer = tagSer;
		this.fileSer = fileSer;
	}

	@GetMapping("/create")
	public String createNews() {
		return "manage/news-create";
	}

	@GetMapping("")
	public void findAll() {
		StringHandler stringHandler = new StringHandler();
		List<News> news = newsSer.findAll();
		news = news.stream().map(o -> {
			o.setDescription(stringHandler.decrypt(o.getDescription()));
			return o;
		}).toList();
		news.forEach(System.out::println);
	}

	@GetMapping("/show-create")
	public String showCreatePage(Model model) {
		NewsCreator creator = new NewsCreator();
		model.addAttribute("creator", creator);
		return "manage/news-create";
	}

	@PostMapping("/save")
	public void insert(@ModelAttribute("news") NewsCreator newsCreator, @RequestParam("banner") MultipartFile file) {
		News news = prepareData(newsCreator, file);
		newsSer.save(news);
	}

	/*-
	 * To save News 
	 * First I will deflater description to minimum size (Notice that description will also contain tag html and img have been encrypted
	 * Then I will save the banner image 
	 * Also I will split all tags to an element of array (Split by ',') and put it to the tags list of news
	 * After that I will save news to db
	 *  
	 * */
	private News prepareData(NewsCreator newsCreator, @RequestParam("banner") MultipartFile file) {
		// Deflater Description
		String description = newsCreator.getDescription();
		StringHandler stringHandler = new StringHandler();
		String output = stringHandler.encrypt(description);
		System.out.println("Before: " + description.getBytes().length);
		System.out.println("After: " + output.getBytes().length);

		String banner = null;
		try {
			banner = fileSer.saveImage(bannerPath, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date date = new Date(System.currentTimeMillis());
		String[] tagsSplitted = newsCreator.getTags().split(",");
		List<Tag> tags = new LinkedList<Tag>();
		Tag tempTag;
		News news = new News(newsCreator.getId(), output, date, newsCreator.getAuthorName(), banner);
		for (String tag : tagsSplitted) {
			if (tag.isBlank())
				continue;
			tempTag = tagSer.findById(tag);
			if (tempTag == null)
				tempTag = new Tag(tag, date, Arrays.asList(news));
			else
				tempTag.getNews().add(news);
			tags.add(tempTag);
		}
		news.setTags(tags);
		return news;
	}
}
