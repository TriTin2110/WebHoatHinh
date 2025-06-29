package vn.tritin.WebHoatHinh.thread;

import vn.tritin.WebHoatHinh.entity.VideoAnalyst;
import vn.tritin.WebHoatHinh.service.VideoAnalystService;

public class UpdateVideoAnalyst extends Thread {
	private VideoAnalystService videoAnalystService;
	private String videoId;

	public UpdateVideoAnalyst(String videoId, VideoAnalystService videoAnalystService) {
		this.videoId = videoId;
		this.videoAnalystService = videoAnalystService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		VideoAnalyst videoAnalyst = videoAnalystService.selectById(videoId);
		videoAnalyst.setTotalComment(videoAnalyst.getTotalComment() + 1);
		videoAnalystService.update(videoAnalyst);
	}

}
