package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.PostConstruct;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.entity.VideoDetail;
import vn.tritin.WebHoatHinh.service.CategoryService;
import vn.tritin.WebHoatHinh.service.CountryService;
import vn.tritin.WebHoatHinh.service.StudioService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
public class VideoControllerUser {
	private VideoService videoService;
	private CategoryService categoryService;
	private CountryService countryService;
	private StudioService studioService;

	@Autowired
	public VideoControllerUser(VideoService videoService, CategoryService categoryService,
			CountryService countryService, StudioService studioService) {
		this.videoService = videoService;
		this.categoryService = categoryService;
		this.countryService = countryService;
		this.studioService = studioService;
	}

	@PostConstruct
	public void loadAllAttribute() {
		videoService.findAll();
		categoryService.findAll();
		countryService.findAll();
		studioService.findAll();
	}

	@GetMapping("/{videoId}")
	public String getVideo(@PathVariable String videoId, Model model) {
		Video video = videoService.findById(videoId);

		if (video != null) {

			VideoDetail videoDetail = video.getVideoDetail();

			model.addAttribute("video", video);
			model.addAttribute("videoDetail", videoDetail);
			model.addAttribute("videos", videoService.findAll());
		}
		return "video";
	}

	@GetMapping("/")
	public String showHomePage(Model model) {
		List<Video> videos = videoService.findAll();
		model.addAttribute("videos", videos);
		return "index";
	}

}
