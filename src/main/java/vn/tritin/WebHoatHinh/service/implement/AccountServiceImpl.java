package vn.tritin.WebHoatHinh.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

}
