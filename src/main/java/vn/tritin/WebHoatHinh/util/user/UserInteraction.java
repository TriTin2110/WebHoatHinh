package vn.tritin.WebHoatHinh.util.user;

import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

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

	private final String ROLE_USER = "ROLE_USER";

	@Autowired
	public UserInteraction(UserService userSer, RoleInteraction roleInt, MailService mailSer) {
		this.userSer = userSer;
		this.roleInt = roleInt;
		this.mailSer = mailSer;
	}

	@SuppressWarnings("deprecation")
	public User createUser(RegisterUser ru) {
		int age = Calendar.getInstance().get(Calendar.YEAR) - (ru.getDateOfBirth().getYear() + 1900);

		Role role = roleInt.find(ROLE_USER);
		Account account = new Account(ru.getUserName(), new BCryptPasswordEncoder().encode(ru.getPassword()),
				ru.getEmail(), role);
		User user = new User(ru.getFullName(), ru.isGender(), age, ru.getDateOfBirth(), account);

		account.setUser(user);
		return user;
	}

	public boolean addingUser(User user) {
		try {
			user = userSer.add(user);
		} catch (UnexpectedRollbackException e) {
			// TODO: handle exception
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return (user != null);
	}

	public String sendingAuthenticationCode(String email) {
		Random random = new Random();
		String randomCode = random.nextInt(900000) + 100000 + "";
		mailSer.sendMail(email, randomCode);
		return randomCode;
	}
}
