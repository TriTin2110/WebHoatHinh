package vn.tritin.WebHoatHinh.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.tritin.WebHoatHinh.entity.Account;

public interface AccountService extends UserDetailsService {
	public Account selectAccountByUsername(String username);

	public List<Account> selectAll();

	public Account update(Account account);

}
