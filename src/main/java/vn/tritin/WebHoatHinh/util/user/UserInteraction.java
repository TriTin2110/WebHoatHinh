package vn.tritin.WebHoatHinh.util.user;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Role;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.service.UserService;
import vn.tritin.WebHoatHinh.service.util.MailService;
import vn.tritin.WebHoatHinh.util.role.RoleInteraction;

@Component
public class UserInteraction {
	private UserService userSer;
	private RoleInteraction roleInt;
	private MailService mailSer;

	@Autowired
	public UserInteraction(UserService userSer, RoleInteraction roleInt, MailService mailSer) {
		this.userSer = userSer;
		this.roleInt = roleInt;
		this.mailSer = mailSer;
	}

	public User createUser(RegisterUser ru) {
		Role role = roleInt.find(ru.getRole());
		Account account = new Account(ru.getUserName(), new BCryptPasswordEncoder().encode(ru.getPassword()), role);
		User user = new User(ru.getEmail(), ru.getFullName(), null, ru.isGender(), 0, ru.getDateOfBirth(), account);

		account.setUser(user);
		return user;
	}

	public boolean addingUser(User user) {
		try {
			userSer.add(user);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public String sendingAuthenticationCode(String email) {
		Random random = new Random();
		String randomCode = random.nextInt(900000) + 100000 + "";
		mailSer.sendMail(email, randomCode);
		return randomCode;
	}
}
