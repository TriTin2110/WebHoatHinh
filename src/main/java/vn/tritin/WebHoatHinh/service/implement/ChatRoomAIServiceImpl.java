package vn.tritin.WebHoatHinh.service.implement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.ChatRoomAIService;
import vn.tritin.WebHoatHinh.service.VectorStoreService;
import vn.tritin.WebHoatHinh.service.VideoService;

@Service
public class ChatRoomAIServiceImpl implements ChatRoomAIService {
	private VectorStoreService vectorStoreService;
	private ChatClient chatClient;
	private VideoService videoService;

	@Autowired
	public ChatRoomAIServiceImpl(ChatClient chatClient, VideoService videoService,
			VectorStoreService vectorStoreService) {
		this.vectorStoreService = vectorStoreService;
		this.chatClient = chatClient;
		this.videoService = videoService;
	}

	public String searchByDescription(String description) {
		String prompt = chatClient.prompt(description).call().chatResponse().getResult().getOutput().getText();
		Logger.getLogger(this.getClass().getName()).info("Prompt: " + prompt);
		if (!prompt.contains(",")) {
			return prompt;
		} else {
			prompt = prompt.toUpperCase();
			String[] split = prompt.split(",");
			String id = split[0].split(":")[1];
			String categories = split[1].split(":")[1];
			String director = split[2].split(":")[1];
			String language = split[3].split(":")[1];
			int viewers = (split[4].split(":")[1].equalsIgnoreCase("null")) ? 0
					: Integer.parseInt(split[4].split(":")[1]);
			Set<String> ids = vectorStoreService
					.getDataByDescription(id, categories, director, language, viewers, prompt).stream()
					.map(o -> o.getMetadata().get("id").toString()).collect(Collectors.toSet());
			List<Video> videos = videoService.findAll();
			List<Video> results = new LinkedList<Video>();
			for (String videoId : ids) {
				for (Video video : videos) {
					if (videoId.equalsIgnoreCase(video.getId())) {
						results.add(video);
					}
				}
			}
			if (results.isEmpty())
				return "Không có phim nào phù hợp với yêu cầu của bạn!";
			else {
				StringBuilder result = new StringBuilder();
				result.append("<h5>Đây là kết quả phù hợp với yêu cầu của bạn:</h5>");
				for (Video video : results) {
					result.append("Tên phim: " + video.getId() + "<br>");
					result.append("Thể loại: "
							+ video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(","))
							+ "<br>");
					result.append("Đạo diễn: " + video.getDirector() + "<br>");
					result.append("Ngôn ngữ: " + video.getLanguage() + "<br>");
					result.append("Xem phim tại đây: <a href=\"http://localhost:8080/view/" + video.getId()
							+ "\" target=\"_blank\">Tại đây</a><br> <hr>");
				}
				return result.toString();
			}
		}
	}
}
