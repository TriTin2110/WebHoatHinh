package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fullName, avatar;
	private boolean gender;
	private int age;
	private Date dateOfBirth;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "account_id")
	private Account account;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments;

	private final String DEFAULT_AVATAR_NAME = "DefaultAvatar.jpeg";

	public User() {
	}

	public User(String fullName, boolean gender, int age, Date dateOfBirth, Account account) {
		this.fullName = fullName;
		this.avatar = DEFAULT_AVATAR_NAME;
		this.gender = gender;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.account = account;
		this.comments = new LinkedList<Comment>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", avatar=" + avatar + ", gender=" + gender + ", age="
				+ age + ", dateOfBirth=" + dateOfBirth + ", account=" + account + "]";
	}

	@SuppressWarnings("deprecation")
	public User updateProfile(Account account, Account accountInDB, String avatar) {
		User user = account.getUser();
		String fullName = user.getFullName();
		boolean gender = user.isGender();
		int age = (new Date(System.currentTimeMillis()).getYear() + 1900) - user.getDateOfBirth().getYear() + 1900;
		Date dateOfBirth = user.getDateOfBirth();

		User userInDB = accountInDB.getUser();
		userInDB.setFullName(fullName);
		userInDB.setAvatar(avatar);
		userInDB.setGender(gender);
		userInDB.setAge(age);
		userInDB.setDateOfBirth(dateOfBirth);
		return userInDB;
	}

}
