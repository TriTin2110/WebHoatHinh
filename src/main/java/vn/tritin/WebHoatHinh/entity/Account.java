package vn.tritin.WebHoatHinh.entity;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import vn.tritin.WebHoatHinh.service.util.Encoder;

//Account will implements UserDetails cause
//Spring Security will use UserDetails for checking when user logins
@Entity
public class Account implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	private String username;
	private String password;
	private String email;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@OneToOne(mappedBy = "account", cascade = { CascadeType.MERGE })
	private User user;

	public Account() {
	}

	public Account(String username, String password, String email, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Encoder.encode(password);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", role=" + role + "]";
	}

	public String showUser() {
		return "Account [user=" + user + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("User"));
	}
}
