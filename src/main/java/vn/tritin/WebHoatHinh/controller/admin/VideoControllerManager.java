package vn.tritin.WebHoatHinh.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoAlreadyExistsException;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoNotFoundException;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.util.video.AttributeAddition;

@Controller
@RequestMapping("/admin/video")
public class VideoControllerManager {
	private VideoService service;
	private AttributeAddition addition;

	@Value("${path.video}")
	private String pathVideo;

	@Value("${path.avatar}")
	private String pathAvatar;

	@Autowired
	public VideoControllerManager(VideoService service, AttributeAddition addition) {
		this.service = service;
		this.addition = addition;
	}

	@PostMapping("")
	public String postVideo(@ModelAttribute("video") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile videoFile, @RequestParam("avatar") MultipartFile avatarFile) {
		Video video = service.findById(videoCreator.getId());
		if (video != null) {
			throw new VideoAlreadyExistsException();
		} else {
			String storingVideoPath = null;
			String storingAvatarPath = null;
			try {
				storingVideoPath = service.saveFile(pathVideo, videoFile);
				storingAvatarPath = service.saveFile(pathAvatar, avatarFile);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			videoCreator.setPathVideo(storingVideoPath);
			videoCreator.setPathAvatar(storingAvatarPath);

			video = addition.createAttribute(videoCreator);
			service.save(video);
			service.updateCache();
			return "redirect:/admin/video";
		}
	}

	@GetMapping("")
	public String showListVideo(Model model) {
		List<Video> videos = service.findAll();
		model.addAttribute("videos", videos);
		return "/manage/video/video-list";
	}

	@GetMapping("/create")
	public String createVideo(Model model) {
		VideoCreator creator = new VideoCreator();
		model.addAttribute("creator", creator);
		model.addAttribute("presentId", "");
		return "/manage/video/video-create";
	}

	@GetMapping("/delete/{id}")
	public String deleteVideo(@PathVariable("id") String id) {
		if (service.isExists(id)) {
			service.delete(id);
			service.updateCache();
		}

		else
			throw new VideoNotFoundException();
		return "redirect:/admin/video";
	}

}
