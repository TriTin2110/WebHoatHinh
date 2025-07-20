package vn.tritin.WebHoatHinh.service.implement;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAOAccount;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.model.AccountDTO;
import vn.tritin.WebHoatHinh.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	private DAOAccount dao;
	private LinkedList<Account> accounts;

	@Autowired
	public AccountServiceImpl(DAOAccount dao) {
		this.dao = dao;
		accounts = new LinkedList<Account>();
	}

	@Override
	public Account selectAccountByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<Account> opt = dao.findById(username);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		Account account = selectAccountByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("Đăng nhập không thành công!");
		}
		return account;
	}

	@Override
	@Cacheable("accounts")
	public List<Account> selectAll() {
		// TODO Auto-generated method stub
		return accounts;
	}

	@Override
	public AccountDTO selectAccountByEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email);
	}

	@CachePut("accounts")
	public List<Account> updateListUser(Account account) {
		for (Account accInList : accounts) {
			if (accInList.getUsername().equals(account.getUsername()))
				return this.accounts;
		}
		accounts.add(account);
		return this.accounts;
	}

	@Transactional
	@Override
	public void updatePassword(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		dao.updatePassword(accountDTO.getPassword(), accountDTO.getUsername());
	}

	@Transactional
	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		try {
			return dao.saveAndFlush(account);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isUsernameExists(String username) {
		// TODO Auto-generated method stub
		return dao.existsById(username);
	}

	@Override
	public boolean isEmailExists(String email) {
		// TODO Auto-generated method stub
		return dao.existsByEmail(email);
	}
};