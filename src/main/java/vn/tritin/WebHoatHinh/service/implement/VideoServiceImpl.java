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
import vn.tritin.WebHoatHinh.util.StringHandler;

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
	public void saveAndFlush(Video video) {
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
		StringHandler handler = new StringHandler();
		return videos.stream().map(o -> {
			if (o.getDescription() != null)
				o.setDescription(handler.decrypt(o.getDescription()));
			return o;
		}).toList();
	}

	@CachePut("videos")
	public List<Video> updateCache() {
		List<Video> videos = dao.findAll();
		StringHandler handler = new StringHandler();
		return videos.stream().map(o -> {
			if (o.getDescription() != null)
				o.setDescription(handler.decrypt(o.getDescription()));
			return o;
		}).toList();
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
		for (int i = 0; i < totalVideo; i += count) { // Seperator videos into small group each group will has {count}
														// element
			count = (totalVideo > i + numberVideoPerLine) ? numberVideoPerLine : (totalVideo - i);
			groupVideos.add(videos.subList(i, i + count));

		}
		return groupVideos;
	}

	public List<Video> getVideosByAmount(List<Video> videos, int amount) {
		return videos.subList(MINIMUM_AMOUNT, amount);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public boolean isExists(String id) {
		return dao.existsById(id);
	}

	@Override
	public boolean isFileExists(String path, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(path + fileName);
		if (file.exists())
			return true;
		return false;
	}

	@Override
	public List<Video> findVideosByListId(List<String> ids) {
		// TODO Auto-generated method stub
		List<Video> videos = findAll();
		List<Video> results = new ArrayList<Video>();
		for (Video video : videos) {
			if (ids.contains(video.getId())) {
				results.add(video);
			}

		}
		return results;
	}

}
