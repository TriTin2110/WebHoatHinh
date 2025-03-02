package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;
import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.CategoryService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
public class VideoControllerUser {
	private List<Video> videos;
	private List<Category> categories;

	private VideoService videoService;
	private CategoryService categoryService;

	@Autowired
	public VideoControllerUser(VideoService videoService, CategoryService categoryService) {
		this.videoService = videoService;
		this.categoryService = categoryService;
	}

	@PostConstruct
	public void findAllData() {
		videos = videoService.findAll();
		categories = categoryService.findAll();
	}

	@GetMapping("/view/{videoId}")
	public String getVideo(@PathVariable String videoId, Model model) {
		Video video = videos.stream().filter(o -> o.getId().equals(videoId)).findFirst().get();

		if (video != null) {
			// Increase Viewer
			video.setViewer(video.getViewer() + 1);
			videos.remove(video);
			videos.add(video);

			model.addAttribute("categories", video.getCategories());
			model.addAttribute("video", video);
			model.addAttribute("videoDetail", video.getVideoDetail());
			model.addAttribute("videos", videos);
		}
		return "video";
	}

	@GetMapping("/")
	public String showHomePage(Model model) {
		model.addAttribute("categories", categories);
		model.addAttribute("videos", videos);
		return "index";
	}

	@GetMapping("/searching-video")
	public String findVideoByName(@RequestParam("content-searched") String name, Model model) {
		List<Video> foundVideos = videoService.getVideoByName(videos, name);
		System.out.println(foundVideos.size());
		model.addAttribute("videos", foundVideos);
		model.addAttribute("categories", categories);
		return "index";
	}
}
