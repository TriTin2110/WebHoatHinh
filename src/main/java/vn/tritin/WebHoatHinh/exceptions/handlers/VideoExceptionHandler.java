package vn.tritin.WebHoatHinh.exceptions.handlers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoAlreadyExistsException;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoNotFoundException;
import vn.tritin.WebHoatHinh.service.VideoService;

@ControllerAdvice
public class VideoExceptionHandler {
	private VideoService videoService;

	public VideoExceptionHandler(VideoService videoService) {
		this.videoService = videoService;
	}

	@ExceptionHandler(exception = { VideoAlreadyExistsException.class, VideoNotFoundException.class })
	public String handlingVideoExistsException(VideoAlreadyExistsException ex, Model model) {
		List<Video> videos = videoService.findAll();
		model.addAttribute("videos", videos);
		model.addAttribute("error", ex.getMessage());
		return "/manage/video/video-list";
	}
}
