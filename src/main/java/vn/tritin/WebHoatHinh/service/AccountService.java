package vn.tritin.WebHoatHinh.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.tritin.WebHoatHinh.entity.Account;

public interface AccountService extends UserDetailsService {
	public Account selectAccountByUsername(String username);
}
