package vn.tritin.WebHoatHinh.service;

import java.util.List;

import vn.tritin.WebHoatHinh.entity.VideoAnalyst;

public interface VideoAnalystService {
	public void add(VideoAnalyst videoAnalyst);

	public void update(VideoAnalyst videoAnalyst);

	public VideoAnalyst selectById(String id);

	public List<VideoAnalyst> selectAll();
}
