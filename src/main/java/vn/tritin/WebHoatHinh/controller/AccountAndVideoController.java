package vn.tritin.WebHoatHinh.controller;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.service.AccountService;

@Controller
public class AccountAndVideoController {
	private AccountService accSer;
	private List<Video> videos;

	@Autowired
	public AccountAndVideoController(AccountService accSer, List<Video> videos) {
		this.accSer = accSer;
		this.videos = videos;
	}

	@GetMapping("/profile/{userName}")
	public String showProfile(@PathVariable("userName") String userName, Model model) {
		Account account = accSer.selectAccountByUsername(userName);
		if (account == null)
			return "user/sign-in";

		String email = account.getEmail();
		String fullName = account.getUser().getFullName();
		String avatar = account.getUser().getAvatar();
		boolean gender = account.getUser().isGender();
		Date dateOfBirth = account.getUser().getDateOfBirth();
		RegisterUser ru = new RegisterUser(email, fullName, avatar, gender, dateOfBirth);
		List<Video> videosSuggesttion = new LinkedList<Video>();

		int i = videos.size() - 1;
		while (videosSuggesttion.size() <= 4 && i >= 0) {
			videosSuggesttion.add(videos.get(i));
			i--;
		}
		videos.forEach(o -> System.out.println(o.getId()));
		model.addAttribute("ru", ru);
		model.addAttribute("videos", videosSuggesttion);
		return "/user/profile";
	}
}
