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
	private String pathVideo;

	@Value("${path.avatar}")
	private String pathAvatar;

	@Autowired
	public VideoController(VideoService service, AttributeAddition addition) {
		this.service = service;
		this.addition = addition;
	}

	@PostMapping("/saving")
	public void postVideo(@ModelAttribute("video") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile videoFile, @RequestParam("avatar") MultipartFile avatarFile) {
		Video video = service.findById(videoCreator.getId());
		String storingVideoPath = null;
		String storingAvatarPath = null;
		try {
			storingVideoPath = service.saveFile(pathVideo, videoFile);
			storingAvatarPath = service.saveFile(pathAvatar, avatarFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		videoCreator.setPathVideo(storingVideoPath);
		videoCreator.setPathAvatar(storingAvatarPath);

		video = addition.createAttribute(videoCreator);
		service.save(video);
		service.updateCache();
	}

}
