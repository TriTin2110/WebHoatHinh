package vn.tritin.WebHoatHinh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.util.video.AttributeAddition;

@RestController
@RequestMapping("/video/admin")
public class VideoController {
	private VideoService service;
	private AttributeAddition addition;
	@Value("${path.video}")
	private String path;

	@Autowired
	public VideoController(VideoService service, AttributeAddition addition) {
		this.service = service;
		this.addition = addition;
	}

	@PostMapping("/post")
	public void postVideo(@ModelAttribute("video") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile file) {
		Video video = service.findById(videoCreator.getId());
		if (video != null) {
		} else {
			String storingPath = null;
			try {
				storingPath = service.postVideo(path, file);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return;
			}
			video = addition.createAttribute(videoCreator);
			video = addition.createOtherEntity(videoCreator, video);
			video.setVideoDetail(addition.createVideoDetail(video, storingPath));
			video.setVideoTag(addition.createVideoTag(video));
			service.save(video);
		}
	}
}
