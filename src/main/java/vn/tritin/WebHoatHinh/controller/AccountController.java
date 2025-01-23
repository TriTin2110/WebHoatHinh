package vn.tritin.WebHoatHinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.RegisterUser;
import vn.tritin.WebHoatHinh.entity.Role;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.RoleService;
import vn.tritin.WebHoatHinh.service.UserService;

@RestController
public class AccountController {
	private AccountService accSer;
	private RoleService roleSer;
	private UserService userSer;

	@Autowired
	public AccountController(AccountService accSer, RoleService roleSer, UserService userSer) {
		this.accSer = accSer;
		this.roleSer = roleSer;
		this.userSer = userSer;
	}

	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}

	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		model.addAttribute("ru", new RegisterUser());
		return "sign-up";
	}

	@GetMapping("/sign-in")
	public String showSignInPage() {
		return "sign-in";
	}

	@PostMapping("/create-user")
	public String checkingRegisterUser(@ModelAttribute("ru") RegisterUser ru, BindingResult result) {
		Account accountInDB = accSer.selectAccountByUsername(ru.getUserName());
		if (accountInDB != null)
			return "Không thành công";
		else if (result.hasErrors()) {
			return "Không thành công lỗi result";
		} else {
			User user = createUser(ru);
			boolean addingResult = addingUser(user);
			if (addingResult)
				return "Thành công";
			else
				return "Không thành công";
		}

	}

	public User createUser(RegisterUser ru) {
		Role role = roleSer.selectById(ru.getRole());
		role = (role == null) ? new Role(ru.getRole()) : role;
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
}
