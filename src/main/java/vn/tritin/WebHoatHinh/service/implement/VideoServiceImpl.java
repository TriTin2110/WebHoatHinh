package vn.tritin.WebHoatHinh.service.implement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
		Video video = (opt.isEmpty()) ? null : opt.get();
		return video;
	}

	@Override
	@Transactional
	public void save(Video video) {
		// TODO Auto-generated method stub
		dao.save(video);
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
		return dao.findAll();
	}

	@CachePut("videos")
	public List<Video> updateCache() {
		return dao.findAll();
	}

}
