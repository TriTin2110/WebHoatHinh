package vn.tritin.WebHoatHinh.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.util.Encoder;
import vn.tritin.WebHoatHinh.service.util.MailService;
import vn.tritin.WebHoatHinh.util.user.UserInteraction;

@Controller
@RequestMapping("/account")
public class AccountController {
	private UserInteraction userInt;
	private AccountService accSer;
	private HttpSession session;
	private List<String> codesEmail;
	private MailService mailSer;

	@Autowired
	public AccountController(UserInteraction userInt, AccountService accSer, MailService mailSer,
			List<String> codesEmail) {
		this.userInt = userInt;
		this.accSer = accSer;
		this.mailSer = mailSer;
		this.codesEmail = codesEmail;
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
	public String showSignInPage(Model model) {
		model.addAttribute("ru", new RegisterUser());
		return "user/sign-in";
	}

	@PostMapping("/create-user")
	public String checkingRegisterUser(@Valid @ModelAttribute("ru") RegisterUser ru, BindingResult result, Model model,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "user/sign-up";
		} else {
			User user = userInt.createUser(ru);
			boolean addingResult = userInt.addingUser(user);
			if (addingResult) {
				return "redirect:sign-in";
			} else {
				model.addAttribute("errors", "Tài khoản đã tồn tại!");
				return "redirect:account/sign-up";
			}

		}
	}

	@PostMapping("/checking-in")
	public String checkingAccount(@Valid @ModelAttribute("ru") RegisterUser ru, BindingResult result, Model model,
			HttpServletRequest request) {
		session = request.getSession();
		Account account = accSer.selectAccountByUsername(ru.getUserName());
		if (result.hasErrors()) {
			return "user/sign-in";
		} else if (account == null) {
			model.addAttribute("errors", "Sai tên tài khoản hoặc mật khẩu!");
			return "user/sign-in";
		} else if (!Encoder.check(ru.getPassword(), account.getPassword())) {
			model.addAttribute("errors", "Sai tên tài khoản hoặc mật khẩu!");
			return "user/sign-in";
		}
		session.setAttribute("account", account);
		return "redirect:/";
	}

	@GetMapping("/show-password-resolve")
	public String showPasswordResolve() {
		return "user/password-resolve";
	}

	@PostMapping("/sending-code-to-gmail")
	public String sendingCodeToGmail(@RequestParam("email") String gmail, Model model) {
		Random random = new Random();
		int code = random.nextInt(100000, 999999);
		mailSer.sendMail(gmail, String.valueOf(code));
		codesEmail.add(Encoder.base64Encode(code));
		return "user/password-enter-code";
	}

	@GetMapping("/checking-code")
	public void checkingCode(@RequestParam("code") String code, Model model) {
		String codeEncrypted = Encoder.base64Encode(code);
		boolean codeIsCorrected = codesEmail.contains(codeEncrypted);
		if (!codeIsCorrected) {
			model.addAttribute("erorrs", "Mã xác thực không chính xác!");
			System.out.println("Mã xác thực không chính xác!");
		} else {
			System.out.println("Chuyển đến trang tiếp theo!");
		}
	}
}
