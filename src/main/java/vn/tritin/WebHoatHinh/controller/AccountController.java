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
import vn.tritin.WebHoatHinh.entity.User;
import vn.tritin.WebHoatHinh.model.AccountDTO;
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
	private Map<String, AccountDTO> passwordForgotCodeAndAccount;
	private MailService mailSer;

	@Autowired
	public AccountController(UserInteraction userInt, AccountService accSer, MailService mailSer,
			List<String> codesEmail) {
		this.userInt = userInt;
		this.accSer = accSer;
		this.mailSer = mailSer;
		this.passwordForgotCodeAndAccount = new HashMap<String, AccountDTO>();
	}

	/*-
	 * RegisterUser (it's DTO) is the model contain all necessary data when the user
	 * sign up
	 * I will use this RegisterUser to get all data inputed from user 
	 * Then the data will be transfer to some data classes (Account, User) 
	 */
	@GetMapping("/sign-up")
	public String showSignUpPage(Model model) {
		model.addAttribute("ru", new RegisterUser());
		return "user/sign-up";
	}

	@GetMapping("/sign-in")
	public String showSignInPage() {
		return "user/sign-in";
	}

	/*-
	 * Create new User then save to the DB
	 * */
	@PostMapping("/create-user")
	public String checkingRegisterUser(@Valid @ModelAttribute("ru") RegisterUser ru, BindingResult result,
			Model model) {
		if (result.hasErrors()) { // When data is not valid
			return "user/sign-up";
		} else {
			User user = userInt.createUser(ru);
			boolean addingSuccess = userInt.addingUser(user);
			if (addingSuccess) {
				return "redirect:sign-in";
			} else {
				model.addAttribute("errors", "Tài khoản đã tồn tại!");
				return "redirect:account/sign-up";
			}

		}
	}

	/*-
	 * Cause we will need user information to display on their session 
	 * So I get all their information when they logged in 
	 * After all redirect to index.html with user session
	 */
	@PostMapping("/generate-user-session")
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

	/*-
	 * Show password forgotten page, asking user provide their gmail
	 * */
	@GetMapping("/show-password-resolve")
	public String showPasswordResolve() {
		return "user/password-resolve";
	}

	/*-
	 * Finding Account by the gmail user inputed
	 * If Account is available we will create a random code, send it to user gmail
	 * So the random code will be hold on the server
	 * When user send the code they have received we will check it
	 * */
	@PostMapping("/sending-code-to-gmail")
	public String sendingCodeToGmail(@RequestParam("email") String gmail, Model model) {
		AccountDTO accountDTO = accSer.selectAccountByEmail(gmail);
		if (accountDTO == null) {
			model.addAttribute("errors", "Tài khoản không tồn tại!");
			return "user/password-resolve";
		}
		Random random = new Random();
		int code = random.nextInt(100000, 999999);
		mailSer.sendMail(gmail, String.valueOf(code));
		passwordForgotCodeAndAccount.put(Encoder.base64Encode(code), accountDTO); // Add code authenticate for user by
																					// email
		return "user/password-enter-code";
	}

	@GetMapping("/checking-code")
	public String checkingCode(@RequestParam("code") String code, Model model) {
		String codeEncrypted = Encoder.base64Encode(code);
		boolean codeIsCorrected = passwordForgotCodeAndAccount.containsKey(codeEncrypted);
		if (!codeIsCorrected) {
			model.addAttribute("erorrs", "Mã xác thực không chính xác!");
			return "user/password-enter-code";
		} else {
			model.addAttribute("code", code);
			model.addAttribute("userName", passwordForgotCodeAndAccount.get(codeEncrypted).getUsername());
			return "user/password-input";
		}
	}

	/*-
	 *	Here I use the AccountDTO to get email, password, username
	 *	The reason why we use DTO class for optimate the HQL query 
	 *	If account updated success, the AccountDTO will we remove from the Map
	 * */
	@PostMapping("/update-password")
	public String updatePassword(@RequestParam("code") String code, @RequestParam("password") String password) {
		AccountDTO accountDTO = passwordForgotCodeAndAccount.get(Encoder.base64Encode(code));
		accountDTO.setPassword(Encoder.encode(password));
		accSer.updateDTO(accountDTO);
		passwordForgotCodeAndAccount.remove(Encoder.base64Encode(code));
		return "user/sign-in";
	}

}
