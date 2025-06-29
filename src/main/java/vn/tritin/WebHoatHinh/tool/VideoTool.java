package vn.tritin.WebHoatHinh.tool;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.VideoService;

@Component
public class VideoTool {
	private VideoService videoService;

	@Autowired
	public VideoTool(VideoService videoService) {
		this.videoService = videoService;
	}

	// truyền List<String> ids vào để lấy prompt videos
	public String getVideo(List<String> ids) {
		List<Video> videos = videoService.findVideosByListId(ids);
		StringBuilder result = new StringBuilder();
		for (Video video : videos) {
			result.append("Tên phim: " + video.getId());
			result.append("Thể loại: "
					+ video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(",")));
			result.append("Ngôn ngữ: " + video.getLanguage());
			result.append("Ngày phát hành: " + video.getDateUploaded());
			result.append("Lượt xem: " + video.getViewer() + "\n");
			result.append("Link: localhost:8080/view/" + video.getId());
		}
		return result.toString();
	}
}
