package vn.tritin.WebHoatHinh.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

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
			saveVideo(videoCreator, videoFile, avatarFile, video);
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
		} else
			throw new VideoNotFoundException();
		return "redirect:/admin/video";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("creator") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile videoFile, @RequestParam("avatar") MultipartFile avatarFile) {
		Video video = service.findById(videoCreator.getId());
		if (video == null) {
			throw new VideoNotFoundException();
		} else {
			saveVideo(videoCreator, videoFile, avatarFile, video);
			return "redirect:/admin/video";
		}
	}

	/*-
	 * I will use this method for both insert and update (video)
	 * */
	private boolean saveVideo(VideoCreator videoCreator, MultipartFile videoFile, MultipartFile avatarFile,
			Video video) {
		System.out.println(videoCreator.getDescription());
		String storingVideoPath = videoFile.getOriginalFilename();
		String storingAvatarPath = avatarFile.getOriginalFilename();
		File file = new File("./logs.txt");
		String result = service.isFileExists(pathVideo + File.separator, videoFile.getOriginalFilename()) + "";
		try {
			Files.write(file.toPath(), result.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (!service.isFileExists(pathVideo + File.separator, videoFile.getOriginalFilename()))
				storingVideoPath = service.saveFile(pathVideo, videoFile);
			if (!service.isFileExists(pathAvatar + File.separator, avatarFile.getOriginalFilename()))
				storingAvatarPath = service.saveFile(pathAvatar, avatarFile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		videoCreator.setPathVideo(storingVideoPath);
		videoCreator.setPathAvatar(storingAvatarPath);

		video = addition.createAttribute(videoCreator);
		service.saveAndFlush(video);
		service.updateCache();
		return true;
	}

	@GetMapping("/show-update/{id}")
	public String showUpdatePage(@PathVariable("id") String id, Model model) {
		Video video = null;
		List<Video> videos = service.findAll();
		for (Video v : videos) {
			if (id.equals(v.getId())) {
				video = v;
				break;
			}

		}
		if (video == null)
			throw new VideoNotFoundException();
		else {
			VideoCreator creator = new VideoCreator();
			String categories = video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(","));
			creator.setId(video.getId());
			creator.setDirector(video.getDirector());
			creator.setLanguage(video.getLanguage());
			creator.setStudio(video.getStudio().getName());
			creator.setPathAvatar(video.getBanner());
			creator.setPathVideo(video.getVideoDetail().getPath());
			creator.setCategories(categories);
			creator.setCountry(video.getCountry().getName());
			creator.setDescription(video.getDescription());
			model.addAttribute("creator", creator);
			return "/manage/video/video-create";
		}
	}

}
