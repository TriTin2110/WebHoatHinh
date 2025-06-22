package vn.tritin.WebHoatHinh.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.Comment;
import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.entity.VideoDetail;
import vn.tritin.WebHoatHinh.service.CategoryService;
import vn.tritin.WebHoatHinh.service.CommentService;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
public class VideoController {
	private final int AMOUNT_NEWS_ON_PAGE = 2;
	private final int AMOUNT_VIDEO_PER_LINE = 5;
	private final int SLIDER_LENGTH = 8;
	private final int NUMBER_VIDEO_IN_ONE_SLIDE = 4;
	private final int NUMBER_VIDEO_ON_PAGE = 10;

	private VideoService videoService;
	private CommentService commmentService;
	private NewsService newsService;
	private CategoryService categoryService;

	@Autowired
	public VideoController(VideoService videoService, CategoryService categoryService, CommentService commmentService,
			NewsService newsService) {
		this.videoService = videoService;
		this.categoryService = categoryService;
		this.commmentService = commmentService;
		this.newsService = newsService;
	}

	// Redirect user to the Video page (index page if the video requested not found)
	@GetMapping("/view/{videoId}")
	public String getVideo(@PathVariable String videoId, Model model, HttpServletRequest request) {
		List<Video> videos = this.videoService.findAll();
		Account account = (Account) request.getSession().getAttribute("account");
		for (Video video : videos) {
			if (video.getId().equals(videoId)) {
				video.setViewer(video.getViewer() + 1);

				VideoDetail videoDetail = video.getVideoDetail();
				List<List<Video>> groupVideos = videoService.getGroupVideo(videos, SLIDER_LENGTH,
						NUMBER_VIDEO_IN_ONE_SLIDE); // Suggestion videos
				List<Category> categories = video.getCategories();

				if (account != null) {
					// Get comment on video (apply when user authenticate)
					List<Comment> comments = this.commmentService.selectByVideoDetail(videoDetail);
					List<News> news = newsService.getNewestNews(AMOUNT_NEWS_ON_PAGE);

					model.addAttribute("comments", comments);
					model.addAttribute("account", account);
					model.addAttribute("news", news);
				}
				model.addAttribute("video", video);
				model.addAttribute("videoDetail", videoDetail);
				model.addAttribute("categories", categories);
				model.addAttribute("videos", groupVideos);
				return "video";
			}
		}
		return "index";
	}

	@GetMapping("/{page-number}")
	public String showHomePage(Model model, HttpServletRequest request, @PathVariable("page-number") int num) {
		List<Integer> pageNumbers = new LinkedList<Integer>();
		List<Video> videos = this.videoService.findAll();
		int count = 1;
		for (int i = 0; (i + 10) < videos.size(); i += 10) {
			pageNumbers.add(count);
			count++;
		}
		pageNumbers.add(count);
		int start = (num - 1) * NUMBER_VIDEO_ON_PAGE;
		int end = (num * NUMBER_VIDEO_ON_PAGE < videos.size()) ? (num * NUMBER_VIDEO_ON_PAGE) : videos.size();
		videos = videos.subList(start, end);
		List<List<Video>> groupVideos = this.videoService.getGroupVideo(videos, videos.size(), AMOUNT_VIDEO_PER_LINE);
		model = setupBasicModel(model, request, videos);
		model.addAttribute("videos", groupVideos);
		model.addAttribute("pageNumber", pageNumbers);
		model.addAttribute("currentPage", num);
		return "index";
	}

	@GetMapping("/searching-video")
	public String findVideoByName(@RequestParam("content-searched") String name, Model model,
			HttpServletRequest request) {
		List<Video> videos = this.videoService.findAll();
		List<Video> foundVideos = videoService.getVideoByName(videos, name);
		List<List<Video>> groupFoundVideos = videoService.getGroupVideo(foundVideos, foundVideos.size(),
				AMOUNT_VIDEO_PER_LINE);
		model = setupBasicModel(model, request, foundVideos);
		model.addAttribute("videos", groupFoundVideos);
		return "index";
	}

	@GetMapping("/filter-category")
	public String filterVideoByCategory(@RequestParam("category") String categoryName, Model model,
			HttpServletRequest request) {
		List<Video> filtedVideos = new LinkedList<Video>();
		List<Video> videos = this.videoService.findAll();

		out: for (Video video : videos) {
			List<Category> categories = video.getCategories();
			for (Category category : categories) {
				if (category.getName().equals(categoryName)) {
					filtedVideos.add(video);
					continue out;
				}
			}
		}
		List<List<Video>> groupFoundVideos = videoService.getGroupVideo(filtedVideos, filtedVideos.size(),
				AMOUNT_VIDEO_PER_LINE);
		model = setupBasicModel(model, request, videos);
		model.addAttribute("videos", groupFoundVideos);
		return "index";
	}

	// Any API will also have 1 basic model
	private Model setupBasicModel(Model model, HttpServletRequest request, List<Video> videos) {
		List<News> news = this.newsService.findAll();
		List<Category> categories = this.categoryService.findAll();
		Account account = (Account) request.getSession().getAttribute("account");

		model.addAttribute("categories", categories);
		model.addAttribute("videos", videos);
		model.addAttribute("news", news);
		model.addAttribute("account", account);
		return model;
	}

}
