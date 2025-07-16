package vn.tritin.WebHoatHinh.thread;

import java.util.stream.Collectors;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.service.VectorStoreService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.util.StringHandler;

public class UpdateViewer extends Thread {
	private Video video;
	private VectorStoreService vectorStoreService;
	private VideoService videoService;

	public UpdateViewer(Video video, VectorStoreService vectorStoreService, VideoService videoService) {
		this.video = video;
		this.vectorStoreService = vectorStoreService;
		this.videoService = videoService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		StringHandler stringHandler = new StringHandler();
		video.setDescription(stringHandler.encrypt(video.getDescription()));
		VectorStoreDTO dto = new VectorStoreDTO(video.getId(),
				video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(",")),
				video.getDirector(), video.getLanguage(), video.getDescription(), video.getViewer());
		videoService.saveAndFlush(video);
		vectorStoreService.updateData(dto);
		videoService.updateCache();
	}
}
