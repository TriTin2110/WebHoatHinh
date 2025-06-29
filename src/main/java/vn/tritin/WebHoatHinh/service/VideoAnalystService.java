package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.VideoAnalyst;

public interface VideoAnalystService {
	public void add(VideoAnalyst videoAnalyst);

	public void update(VideoAnalyst videoAnalyst);

	public VideoAnalyst selectById(String id);
}
