package vn.tritin.WebHoatHinh.model;

/*
 * AccountDTO là một class rút gọn của Account
 * Mục đích của class này giúp lấy ra chỉ những trường cần thiết
 * khi đó câu lệnh truy vấn sẽ đỡ dài hơn
 * */
public class AccountDTO {
	private String email, password, username;

	public AccountDTO() {
	}

	public AccountDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "AccountDTO [email=" + email + ", password=" + password + ", username=" + username + "]";
	}

}
