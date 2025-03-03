package vn.tritin.WebHoatHinh.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAOUser;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private DAOUser dao;

	@Autowired
	public UserServiceImpl(DAOUser dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public User add(User user) {
		try {
			user = dao.save(user);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			return null;
		}
		return user;

	}

	@Override
	@Transactional
	public boolean update(User user) {
		return dao.saveAndFlush(user) != null;
	}

	@Override
	@Transactional
	public void delete(User user) {
		dao.deleteById(user.getId());
	}

	@Override
	public User selectUserByEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email);
	}

}
