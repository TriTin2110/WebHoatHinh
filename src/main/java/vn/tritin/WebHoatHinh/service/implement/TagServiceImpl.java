package vn.tritin.WebHoatHinh.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOTag;
import vn.tritin.WebHoatHinh.entity.Tag;
import vn.tritin.WebHoatHinh.service.TagService;

@Service
public class TagServiceImpl implements TagService {
	private DAOTag dao;

	@Autowired
	public TagServiceImpl(DAOTag dao) {
		this.dao = dao;
	}

	@Override
	public Tag save(Tag tag) {
		// TODO Auto-generated method stub
		return dao.save(tag);
	}

	@Override
	public Tag findById(String id) {
		// TODO Auto-generated method stub
		Optional<Tag> opt = dao.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

}
