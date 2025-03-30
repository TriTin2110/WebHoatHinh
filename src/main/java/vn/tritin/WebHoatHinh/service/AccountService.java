package vn.tritin.WebHoatHinh.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.model.AccountDTO;

public interface AccountService extends UserDetailsService {
	public Account selectAccountByUsername(String username);

	public List<Account> selectAll();

	public AccountDTO selectAccountByEmail(@Param("email") String email);

	public void updatePassword(AccountDTO accountDTO);

	public Account update(Account account);
}
