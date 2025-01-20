package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Account;

public interface AccountService {
	public Account selectAccountByUsername(String username);
}
