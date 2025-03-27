package vn.tritin.WebHoatHinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.model.AccountDTO;

@RepositoryRestResource
public interface DAOAccount extends JpaRepository<Account, String> {
	@Query("SELECT new vn.tritin.WebHoatHinh.model.AccountDTO(userName, password) FROM Account a WHERE a.email= :email")
	public AccountDTO findByEmail(String email);

	/*
	 * The @Modifying annotation is used to enhance the @Query annotation so that we
	 * can execute not only SELECT queries, but also INSERT, UPDATE, DELETE, and
	 * even DDL queries.
	 */
	@Modifying
	@Query("UPDATE Account SET password = :password WHERE userName = :userName")
	public void updateDTO(@Param("password") String password, @Param("userName") String username);
}
