package vn.tritin.WebHoatHinh.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOComment;
import vn.tritin.WebHoatHinh.entity.Comment;
import vn.tritin.WebHoatHinh.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private DAOComment dao;

	@Autowired
	public CommentServiceImpl(DAOComment dao) {
		this.dao = dao;
	}

	@Override
	public Comment save(Comment comment) {
		// TODO Auto-generated method stub
		return this.dao.save(comment);
	}

}
