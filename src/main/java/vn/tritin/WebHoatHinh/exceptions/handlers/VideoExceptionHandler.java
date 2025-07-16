package vn.tritin.WebHoatHinh.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	public ResponseEntity<Map<String, String>> handlingVideoExistsException(VideoAlreadyExistsException ex) {
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", ex.getMessage());
		message.put("result", "false");
		return ResponseEntity.status(200).body(message);
	}
}
