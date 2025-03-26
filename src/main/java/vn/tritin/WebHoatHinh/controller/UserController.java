package vn.tritin.WebHoatHinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userSer;

	@Autowired
	public UserController(UserService userSer) {
		this.userSer = userSer;
	}

	@PostMapping("/save-user")
	public String saveUser(User user) {
		userSer.update(user);
		return "";
	}

}
