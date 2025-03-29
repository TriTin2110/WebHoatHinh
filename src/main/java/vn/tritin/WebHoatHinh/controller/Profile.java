package vn.tritin.WebHoatHinh.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Video;

@Controller
public class Profile {
	private List<Video> videos;

	@Autowired
	public Profile(List<Video> videos) {
		this.videos = videos;
	}

	@GetMapping("/profile")
	public String showProfile(HttpServletRequest request, Model model) {
		Account account = (Account) request.getSession().getAttribute("account");

		List<Video> videosSuggesttion = new LinkedList<Video>();

		int i = videos.size() - 1;
		while (videosSuggesttion.size() <= 4 && i >= 0) {
			videosSuggesttion.add(videos.get(i));
			i--;
		}
		model.addAttribute("account", account);
		model.addAttribute("videos", videosSuggesttion);
		return "/user/profile";
	}
}
