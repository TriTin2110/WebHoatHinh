package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOStudio;
import vn.tritin.WebHoatHinh.entity.Studio;
import vn.tritin.WebHoatHinh.service.StudioService;

@Service
public class StudioServiceImpl implements StudioService {
	private DAOStudio dao;

	@Autowired
	public StudioServiceImpl(DAOStudio dao) {
		this.dao = dao;
	}

	@Override
	public Studio findByName(String name) {
		// TODO Auto-generated method stub
		Optional<Studio> studio = dao.findById(name);
		return studio.isEmpty() ? null : studio.get();
	}

	@Override
	public Studio add(Studio studio) {
		// TODO Auto-generated method stub
		return dao.save(studio);
	}

	@Override
	public Studio merge(Studio studio) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(studio);
	}

	@Override
	@Cacheable("studios")
	public List<Studio> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
