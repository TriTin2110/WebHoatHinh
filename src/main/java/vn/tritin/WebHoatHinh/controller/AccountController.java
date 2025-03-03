package vn.tritin.WebHoatHinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.util.user.UserInteraction;

@Controller
@RequestMapping("/account")
public class AccountController {
	private UserInteraction userInt;

	@Autowired
	public AccountController(UserInteraction userInt) {
		this.userInt = userInt;
	}

	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}

	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		model.addAttribute("ru", new RegisterUser());
		return "user/sign-up";
	}

	@GetMapping("/sign-in")
	public String showSignInPage() {
		return "sign-in";
	}

	@PostMapping("/create-user")
	public String checkingRegisterUser(@Valid @ModelAttribute("ru") RegisterUser ru, BindingResult result,
			Model model) {
//		Account accountInDB = accSer.selectAccountByUsername(ru.getUserName());
//		if (accountInDB != null) {
//			
//			return "user/sign-up";
//		}
//
//		else 
		if (result.hasErrors()) {
			return "user/sign-up";
		} else {
			User user = userInt.createUser(ru);
			boolean addingResult = userInt.addingUser(user);
			if (addingResult)
				return "index";
			else {
				model.addAttribute("errors", "Tài khoản đã tồn tại!");
				return "user/sign-up";
			}

		}

	}

}
