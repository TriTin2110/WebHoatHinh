package vn.tritin.WebHoatHinh.service.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.entity.Video;

@Service
public class VideoSuggestionImpl implements VideoSuggestion {
	private List<Video> videos;

	@Autowired
	public VideoSuggestionImpl(List<Video> videos) {
		this.videos = videos;
	}

	@Override
	public List<Video> getSuggestionVideo() {
		// TODO Auto-generated method stub
		List<Video> videosSuggesttion = new LinkedList<Video>();

		int i = videos.size() - 1;
		while (videosSuggesttion.size() <= 4 && i >= 0) {
			videosSuggesttion.add(videos.get(i));
			i--;
		}
		return videosSuggesttion;
	}

}
