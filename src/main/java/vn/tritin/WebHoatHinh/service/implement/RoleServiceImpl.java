package vn.tritin.WebHoatHinh.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAORole;
import vn.tritin.WebHoatHinh.entity.Role;
import vn.tritin.WebHoatHinh.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private DAORole dao;

	@Autowired
	public RoleServiceImpl(DAORole dao) {
		this.dao = dao;
	}

	@Override
	public Role selectById(String name) {
		// TODO Auto-generated method stub
		Optional<Role> opt = dao.findById(name);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Transactional
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return dao.save(role);
	}

	@Override
	@Transactional
	public void merge(Role role) {
		// TODO Auto-generated method stub
		dao.saveAndFlush(role);
	}

}
