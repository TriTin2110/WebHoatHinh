package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOAccount;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	private DAOAccount dao;

	@Autowired
	public AccountServiceImpl(DAOAccount dao) {
		this.dao = dao;
	}

	@Override
	public Account selectAccountByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<Account> opt = dao.findById(username);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Account accInDB = selectAccountByUsername(username);
		if (accInDB != null) {
			return User.builder().username(accInDB.getUserName()).password(accInDB.getPassword())
					.roles(accInDB.getRole().getName()).build();
		}
		return null;
	}

	@Override
	@Cacheable("users")
	public List<Account> selectAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
