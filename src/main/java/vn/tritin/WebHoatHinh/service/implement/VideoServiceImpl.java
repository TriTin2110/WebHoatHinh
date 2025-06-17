package vn.tritin.WebHoatHinh.service.implement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAOVideo;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
	private final int MINIMUM_AMOUNT = 0;
	private DAOVideo dao;

	@Autowired
	public VideoServiceImpl(DAOVideo dao) {
		this.dao = dao;
	}

	@Override
	@Cacheable("video_id")
	public Video findById(String id) {
		// TODO Auto-generated method stub
		Optional<Video> opt = dao.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	public void save(Video video) {
		// TODO Auto-generated method stub
		dao.saveAndFlush(video);
	}

	@Override
	public String saveFile(String path, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		String resultPath = file.getOriginalFilename();
		String fileName = System.currentTimeMillis()
				+ resultPath.substring(resultPath.lastIndexOf("."), resultPath.length());

		String storingPath = path + File.separator + fileName;

		File pathDir = new File(path);
		if (!pathDir.exists())
			pathDir.mkdirs();

		Files.copy(file.getInputStream(), Path.of(storingPath));
		return fileName;
	}

	@Override
	@Cacheable("videos")
	public List<Video> findAll() {
		List<Video> videos = dao.findAll();
		videos.sort((o1, o2) -> {
			return o2.getDateUploaded().compareTo(o1.getDateUploaded());
		});
		return videos;
	}

	@CachePut("videos")
	public List<Video> updateCache() {
		List<Video> videos = dao.findAll();
		videos.sort((o1, o2) -> {
			return o2.getDateUploaded().compareTo(o1.getDateUploaded());
		});
		return videos;
	}

	/*-
	 * First we will go one by one video in the list
	 * So here i splitted the inputed video name 
	 * Then each word will be compare to the video tittle
	 * If the return is true then the video will be add to the foundVideos list
	 * */
	@Override
	public List<Video> getVideoByName(List<Video> videos, String name) {
		// TODO Auto-generated method stub
		String[] nameSplits = name.split(" ");
		List<Video> foundVideos = new ArrayList<Video>();
		String videoName = null;
		for (Video video : videos) {
			videoName = video.getId().toLowerCase();
			for (String nameSplit : nameSplits) {
				if (videoName.indexOf(nameSplit.toLowerCase()) > -1) {
					foundVideos.add(video);
					continue;
				}
			}
		}
		return foundVideos;
	}

	@Override
	public List<List<Video>> getGroupVideo(List<Video> videos, int totalVideo, int numberVideoPerLine) {
		// TODO Auto-generated method stub
		List<List<Video>> groupVideos = new LinkedList<List<Video>>();
		int count = 0;
		for (int i = 0; i < totalVideo; i += count) { // Chia danh sách video thành từng
			// nhóm 4 phần tử
			count = (totalVideo > i + numberVideoPerLine) ? numberVideoPerLine : (totalVideo - i);
			groupVideos.add(videos.subList(i, i + count));

		}
		return groupVideos;
	}

	public List<Video> getVideosByAmount(List<Video> videos, int amount) {
		return videos.subList(MINIMUM_AMOUNT, amount);
	}

}
