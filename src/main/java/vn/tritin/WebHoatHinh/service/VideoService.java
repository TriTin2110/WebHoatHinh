package vn.tritin.WebHoatHinh.service;

import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;

public interface VideoService {
	public Video findById(String id);

	public void save(Video video);

	public String postVideo(String path, MultipartFile file) throws Exception;
}
