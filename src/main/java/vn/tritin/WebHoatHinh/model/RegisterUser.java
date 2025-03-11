package vn.tritin.WebHoatHinh.model;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUser {
	@NotBlank(message = "Tài khoản không được để trống")
	@Size(min = 3, message = "Tài khoản phải có độ dài lớn hơn 3")
	private String userName;
	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 3, message = "Mật khẩu phải có độ dài lớn hơn 3")
	private String password;
	@Email(message = "Email không hợp lệ")
	private String email;
	private String role, fullName;
	private boolean gender;
	private Date dateOfBirth;

	public RegisterUser() {
	}

	public RegisterUser(String userName, String password, String role, String email, String fullName, boolean gender,
			Date dateOfBirth) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.email = email;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
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
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
