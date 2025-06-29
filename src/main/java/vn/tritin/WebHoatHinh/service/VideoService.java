package vn.tritin.WebHoatHinh.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;

public interface VideoService {
	public Video findById(String id);

	public void saveAndFlush(Video video);

	public List<Video> findAll();

	// Return file's name
	public String saveFile(String path, MultipartFile file) throws Exception;

	public List<Video> updateCache();

	public List<Video> getVideoByName(List<Video> videos, String name);

	public List<List<Video>> getGroupVideo(List<Video> videos, int totalVideo, int numberVideoPerLine);

	public List<Video> getVideosByAmount(List<Video> videos, int amount);

	public void delete(Video video);

	public boolean isExists(String id);

	public boolean isFileExists(String path, String fileName);

	public List<Video> findVideosByListId(List<String> ids);
}
