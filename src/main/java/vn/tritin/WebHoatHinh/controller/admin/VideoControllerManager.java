package vn.tritin.WebHoatHinh.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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
import vn.tritin.WebHoatHinh.entity.VideoAnalyst;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoAlreadyExistsException;
import vn.tritin.WebHoatHinh.exceptions.exceptions.VideoNotFoundException;
import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.service.VectorStoreService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.video.AttributeAddition;
import vn.tritin.WebHoatHinh.util.video.VideoDuration;

@Controller
@RequestMapping("/admin/video")
public class VideoControllerManager {
	private VectorStoreService vectorStoreService;
	private VideoService service;
	private AttributeAddition addition;
	private FileService fileService;
	@Value("${path.video}")
	private String pathVideo;

	@Value("${path.avatar}")
	private String pathAvatar;

	@Autowired
	public VideoControllerManager(VideoService service, AttributeAddition addition, FileService fileService,
			VectorStoreService vectorStoreService) {
		this.service = service;
		this.addition = addition;
		this.fileService = fileService;
		this.vectorStoreService = vectorStoreService;
	}

	@PostMapping("")
	public ResponseEntity<Map<String, String>> postVideo(@ModelAttribute("video") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile videoFile, @RequestParam("avatar") MultipartFile avatarFile) {
		Video video = service.findById(videoCreator.getId());
		if (video != null) {
			throw new VideoAlreadyExistsException(video.getId());
		} else {
			VectorStoreDTO vectorStoreDTO = saveVideo(videoCreator, videoFile, avatarFile, video);
			if (vectorStoreDTO != null)
				vectorStoreService.insertData(vectorStoreDTO);
			Map<String, String> message = new HashMap<String, String>();
			message.put("result", "true");
			message.put("message", "Thêm thành công!");
			return ResponseEntity.status(200).body(message);

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
		Video video = service.findById(id);
		if (video == null) {
			throw new VideoNotFoundException();
		} else {
			service.delete(video);
			vectorStoreService.deleteData(id);
			service.updateCache();
		}

		return "redirect:/admin/video";
	}

	@PostMapping("/update")
	public ResponseEntity<Map<String, String>> update(@ModelAttribute("creator") VideoCreator videoCreator,
			@RequestParam("video") MultipartFile videoFile, @RequestParam("avatar") MultipartFile avatarFile) {
		Video video = service.findById(videoCreator.getId());
		if (video == null) {
			throw new VideoNotFoundException();
		} else {
			VectorStoreDTO vectorStoreDTO = saveVideo(videoCreator, videoFile, avatarFile, video);
			vectorStoreService.updateData(vectorStoreDTO);
			Map<String, String> message = new HashMap<String, String>();
			message.put("result", "true");
			message.put("message", "Đã cập nhật thành công!");
			return ResponseEntity.status(200).body(message);
		}
	}

	/*-
	 * I will use this method for both insert and update (video)
	 * */
	private VectorStoreDTO saveVideo(VideoCreator videoCreator, MultipartFile videoFile, MultipartFile avatarFile,
			Video video) {

		String storingVideoPath = videoFile.getOriginalFilename();
		String storingAvatarPath = avatarFile.getOriginalFilename();
		try {
			if (!fileService.isFileExists(pathVideo + File.separator, videoFile.getOriginalFilename()))
				storingVideoPath = fileService.saveFile(pathVideo, videoFile);
			if (!fileService.isFileExists(pathAvatar + File.separator, avatarFile.getOriginalFilename()))
				storingAvatarPath = fileService.saveFile(pathAvatar, avatarFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		videoCreator.setPathVideo(storingVideoPath);
		videoCreator.setPathAvatar(storingAvatarPath);

		video = addition.createAttribute(videoCreator);
		// Get video duration
		String duration = VideoDuration.getDuration(new File(pathVideo + File.separator + storingVideoPath));
		video.setDuration(duration);
		video.setVideoAnalyst(new VideoAnalyst(video.getId(), video));
		try {
			service.saveAndFlush(video);
		} catch (JpaObjectRetrievalFailureException e) {
			// TODO: handle exception
			e.printStackTrace();
			fileService.removeFile(pathAvatar + File.separator + storingAvatarPath);
			fileService.removeFile(pathVideo + File.separator + storingVideoPath);
			return null;
		}
		VectorStoreDTO vectorStore = new VectorStoreDTO(video.getId(),
				video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(",")),
				video.getDirector(), video.getLanguage(), video.toString(), video.getViewer());
		service.updateCache();
		return vectorStore;
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
