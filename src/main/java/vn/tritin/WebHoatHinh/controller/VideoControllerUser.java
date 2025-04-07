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
import jakarta.servlet.http.HttpSession;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
public class VideoControllerUser {
	private List<Video> videos;
	private List<Category> categories;
	private VideoService videoService;

	@Autowired
	public VideoControllerUser(VideoService videoService, List<Video> videos, List<Category> categories) {
		this.videoService = videoService;
		this.videos = videos;
		this.categories = categories;
	}

	// Redirect user to the Video page (index page if the video requested not found)
	@GetMapping("/view/{videoId}")
	public String getVideo(@PathVariable String videoId, Model model, HttpSession session) {
		for (Video video : videos) {
			if (video.getId().equals(videoId)) {
				video.setViewer(video.getViewer() + 1);
				videos.remove(video);
				videos.add(video);
				model = setupBasicModel(model, session, videos);
				model.addAttribute("video", video);
				model.addAttribute("videoDetail", video.getVideoDetail());
				return "video";
			}
		}
		return "index";
	}

	@GetMapping("/")
	public String showHomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model = setupBasicModel(model, session, videos);
		return "index";
	}

	@GetMapping("/searching-video")
	public String findVideoByName(@RequestParam("content-searched") String name, Model model, HttpSession session) {
		List<Video> foundVideos = videoService.getVideoByName(videos, name);
		model = setupBasicModel(model, session, foundVideos);
		return "index";
	}

	@GetMapping("/filter-category")
	public String filterVideoByCategory(@RequestParam("category") String categoryName, Model model,
			HttpSession session) {
		List<Video> filtedVideos = new LinkedList<Video>();

		out: for (Video video : videos) {
			List<Category> categories = video.getCategories();
			for (Category category : categories) {
				if (category.getName().equals(categoryName)) {
					filtedVideos.add(video);
					continue out;
				}
			}
		}
		model = setupBasicModel(model, session, filtedVideos);
		return "index";
	}

	// Any API will also have 1 basic model
	private Model setupBasicModel(Model model, HttpSession session, List<Video> videos) {
		model.addAttribute("account", (Account) session.getAttribute("account"));
		model.addAttribute("categories", categories);
		model.addAttribute("videos", videos);
		return model;
	}

}
