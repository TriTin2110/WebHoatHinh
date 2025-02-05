package vn.tritin.WebHoatHinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.util.user.UserInteraction;

@RestController
@RequestMapping("/account")
public class AccountController {
	private AccountService accSer;
	private UserInteraction userInt;

	@Autowired
	public AccountController(AccountService accSer, UserInteraction userInt) {
		this.accSer = accSer;
		this.userInt = userInt;
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
			User user = userInt.createUser(ru);
			boolean addingResult = userInt.addingUser(user);
			if (addingResult)
				return "Thành công";
			else
				return "Không thành công";
		}

	}

}
