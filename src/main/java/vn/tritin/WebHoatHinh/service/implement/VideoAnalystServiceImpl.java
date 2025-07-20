package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAOVideoAnalyst;
import vn.tritin.WebHoatHinh.entity.VideoAnalyst;
import vn.tritin.WebHoatHinh.service.VideoAnalystService;

@Service
public class VideoAnalystServiceImpl implements VideoAnalystService {
	private DAOVideoAnalyst dao;

	@Autowired
	public VideoAnalystServiceImpl(DAOVideoAnalyst dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void add(VideoAnalyst videoAnalyst) {
		// TODO Auto-generated method stub
		dao.save(videoAnalyst);
	}

	@Override
	@Transactional
	public void update(VideoAnalyst videoAnalyst) {
		// TODO Auto-generated method stub
		dao.saveAndFlush(videoAnalyst);
	}

	@Override
	public VideoAnalyst selectById(String id) {
		// TODO Auto-generated method stub
		Optional<VideoAnalyst> opt = dao.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public List<VideoAnalyst> selectAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
