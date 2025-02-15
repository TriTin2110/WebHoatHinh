package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.entity.VideoDetail;
import vn.tritin.WebHoatHinh.service.VideoService;

@Controller
public class VideoControllerUser {
	private VideoService service;

	@Autowired
	public VideoControllerUser(VideoService service) {
		this.service = service;
	}

	@GetMapping("/{videoId}")
	public String getVideo(@PathVariable String videoId, Model model) {
		Video video = service.findById(videoId);

		if (video != null) {
			List<Video> videos = service.selectAll();

			VideoDetail videoDetail = video.getVideoDetail();

			model.addAttribute("video", video);
			model.addAttribute("videoDetail", videoDetail);
			model.addAttribute("videos", videos);

		}
		return "video";
	}

	@GetMapping("/")
	public String showHomePage(Model model) {
		List<Video> videos = service.selectAll();
		model.addAttribute("videos", videos);
		return "index";
	}

}
