package vn.tritin.WebHoatHinh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Role;
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.RegisterUser;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.RoleService;
import vn.tritin.WebHoatHinh.service.util.Encoder;
import vn.tritin.WebHoatHinh.service.util.MailService;
import vn.tritin.WebHoatHinh.util.user.UserInteraction;

@Controller
@RequestMapping("/account")
public class AccountController {
	private UserInteraction userInt;
	private AccountService accSer;
	private Map<String, String> codesEmail;
	private MailService mailSer;
	private RoleService roleSer;

	@Autowired
	public AccountController(UserInteraction userInt, AccountService accSer, MailService mailSer,
			List<String> codesEmail, RoleService roleSer) {
		this.userInt = userInt;
		this.accSer = accSer;
		this.mailSer = mailSer;
		this.roleSer = roleSer;
		this.codesEmail = new HashMap<String, String>();
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

	@PostMapping("/generate-user")
	public String checkingAccount(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Account> accounts = accSer.selectAll();
		for (Account account : accounts) {
			if (account.getUserName().equals(authentication.getName())) {
				request.getSession().setAttribute("account", account);
				break;
			}
		}
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
		codesEmail.put(Encoder.base64Encode(code), gmail); // Add code authenticate for user by email
		return "user/password-enter-code";
	}

	@GetMapping("/checking-code")
	public String checkingCode(@RequestParam("code") String code, Model model) {
		String codeEncrypted = Encoder.base64Encode(code);
		String email = codesEmail.get(codeEncrypted);
		if (email == null) {
			model.addAttribute("erorrs", "Mã xác thực không chính xác!");
			return "user/password-enter-code";
		} else {
			User user = userInt.findUserByEmail(email);
			Account account = user.getAccount();
			model.addAttribute("account", account);
			model.addAttribute("role", account.getRole());
			return "user/password-input";
		}
	}

	@PostMapping("/update-password")
	public String updatePassword(@ModelAttribute("account") Account account) {
		// vấn đề: role từ th:field == null
		Role role = roleSer.selectById("USER");
		account.setRole(role);

		accSer.update(account);
		return "user/sign-in";
	}
}
