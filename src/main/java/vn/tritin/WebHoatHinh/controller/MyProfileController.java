package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.service.util.FileServiceImpl;
import vn.tritin.WebHoatHinh.service.util.VideoSuggestion;

@Controller
@RequestMapping("/user-profile")
public class MyProfileController {
	private AccountService accSer;
	private VideoSuggestion videoSuggestSer;
	@Value("${path.user-avatar}")
	private String avatarPath;

	@Autowired
	public MyProfileController(VideoSuggestion videoSuggestSer, AccountService accSer) {
		this.videoSuggestSer = videoSuggestSer;
		this.accSer = accSer;
	}

	@GetMapping("")
	public String showProfile(HttpServletRequest request, Model model) {
		Account account = (Account) request.getSession().getAttribute("account");
		List<Video> videosSuggesttion = videoSuggestSer.getSuggestionVideo();
		model.addAttribute("account", account);
		model.addAttribute("videos", videosSuggesttion);
		return "/user/profile/profile";
	}

	@GetMapping("/{id}")
	public String showOtherProfile(@PathVariable("id") String id, Model model) {
		Account otherAccount = accSer.selectAccountByUsername(id);
		if (otherAccount == null)
			return "index";
		else {
			model.addAttribute("account", otherAccount);
			return "/other/profile/other-profile";
		}
	}

	/*-
	 * This mapping will update the profile information base on the account client request
	 * First will update avatar (if there is any image on request)
	 * Then we will update information on the account and user entity
	 * */
	@PostMapping("/update")
	public String update(@ModelAttribute("account") Account account, @RequestParam("avatar") MultipartFile avatarFile,
			Model model, HttpServletRequest request) {
		Account accountInDB = accSer.selectAccountByUsername(account.getUsername());
		if (accountInDB == null) {
			model.addAttribute("error", "Tài khoản không tồn tại!");
			return "/user/profile";
		}

		String avatar = accountInDB.getUser().getAvatar();
		if (avatarFile.getSize() > 0) { // If user want to change image
			FileService fileSer = new FileServiceImpl();
			avatar = fileSer.saveFile(avatarPath, avatarFile);
		}
		accountInDB.setEmail(account.getEmail());
		accountInDB.setUser(accountInDB.getUser().updateProfile(account, accountInDB, avatar));
		accountInDB = accSer.update(accountInDB);
		request.getSession().setAttribute("account", accountInDB);

		List<Video> videosSuggesttion = videoSuggestSer.getSuggestionVideo();
		model.addAttribute("account", accountInDB);
		model.addAttribute("videos", videosSuggesttion);

		return "redirect:/";
	}
}
