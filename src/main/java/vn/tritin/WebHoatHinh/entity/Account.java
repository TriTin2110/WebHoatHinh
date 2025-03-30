package vn.tritin.WebHoatHinh.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import vn.tritin.WebHoatHinh.service.util.Encoder;

@Entity
public class Account {
	@Id
	private String userName;
	private String password;
	private String email;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@OneToOne(mappedBy = "account", cascade = { CascadeType.MERGE })
	private User user;

	public Account() {
	}

	public Account(String userName, String password, String email, Role role) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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
		return "Account [userName=" + userName + ", password=" + password + ", role=" + role + ", user=" + user + "]";
	}

	public String showUser() {
		return "Account [user=" + user + "]";
	}
}
