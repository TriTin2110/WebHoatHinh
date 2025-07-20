package vn.tritin.WebHoatHinh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.tritin.WebHoatHinh.service.AccountService;

@Configuration
public class AccountSecurity {
	@Bean
	public PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider createAuthenticationProvider(AccountService service,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider(service);
		dao.setPasswordEncoder(passwordEncoder);
		return dao;
	}

	@Bean
	public SecurityFilterChain createChain(HttpSecurity http) {
		try {
			http.authorizeHttpRequests(con -> con.requestMatchers(HttpMethod.GET, "/chat-room").authenticated()
					.requestMatchers(HttpMethod.GET, "/user-profile").authenticated().requestMatchers("/admin/**")
					.hasRole("ADMIN").anyRequest().permitAll())
					.formLogin(login -> login.loginPage("/account/sign-in").loginProcessingUrl("/authenticateTheUser")
							.successForwardUrl("/account/generate-user-session").permitAll())
					.logout(logout -> logout.logoutSuccessUrl("/").permitAll())
					.csrf(csrf -> csrf.ignoringRequestMatchers("/logout", "/admin/create-chat-room", "/exit")); // chỉ
			// tắt
			// csrf
			// với
			// endpoint
			// /logout
			return http.build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

}
