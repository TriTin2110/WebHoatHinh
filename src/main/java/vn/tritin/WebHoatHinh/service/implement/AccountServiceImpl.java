package vn.tritin.WebHoatHinh.service.implement;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
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
		Account accInDB = selectAccountByUsername(username);
		if (accInDB == null)
			throw new UsernameNotFoundException(username + " cannot found!");

		updateListUser(accInDB);
		return User.builder().username(accInDB.getUserName()).password(accInDB.getPassword())
				.roles(accInDB.getRole().getName()).build();
	}

	@Override
	@Cacheable("accounts")
	public List<Account> selectAll() {
		// TODO Auto-generated method stub
		return accounts;
	}

	@CachePut("accounts")
	public List<Account> updateListUser(Account account) {
		for (Account accInList : accounts) {
			if (accInList.getUserName().equals(account.getUserName()))
				return this.accounts;
		}
		accounts.add(account);
		return this.accounts;
	}

	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(account);
	}

}
