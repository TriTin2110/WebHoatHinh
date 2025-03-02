package vn.tritin.WebHoatHinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.service.UserService;
import vn.tritin.WebHoatHinh.util.user.UserInteraction;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userSer;
	private UserInteraction userInt;

	@Autowired
	public UserController(UserService userSer, UserInteraction userInt) {
		this.userSer = userSer;
		this.userInt = userInt;
	}

	@GetMapping("/reset-password")
	public String redirectToResetPasswordPage(Model model) {
		return "reset-password";
	}

	@GetMapping("/find-user-email/{email}")
	public String findUserByEmail(@PathVariable String email, Model model) {
		User user = userSer.selectUserByEmail(email);
		if (user == null) {
			model.addAttribute("error", "Người dùng có email là " + email + " không tồn tại!");
		} else {
			String randomCode = userInt.sendingAuthenticationCode(email);
			model.addAttribute("randomCode", randomCode);
			model.addAttribute("user", user);
		}
		return "";
	}

	@PostMapping("/save-user")
	public String saveUser(User user) {
		userSer.update(user);
		return "";
	}

}
