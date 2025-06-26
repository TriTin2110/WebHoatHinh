package vn.tritin.WebHoatHinh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.tritin.WebHoatHinh.service.AccountService;

@Configuration
public class AccountSecurity {
	@Bean
	public DaoAuthenticationProvider createAuthenticationProvider(AccountService service) {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(service);
		dao.setPasswordEncoder(new BCryptPasswordEncoder());
		return dao;
	}

	@Bean
	public SecurityFilterChain createChain(HttpSecurity http) {
		try {
			http.authorizeHttpRequests(con -> con.requestMatchers(HttpMethod.GET, "/").authenticated()
					.requestMatchers(HttpMethod.GET, "/chat-room").authenticated().anyRequest().permitAll())
					.formLogin(login -> login.loginPage("/account/sign-in").loginProcessingUrl("/authenticateTheUser")
							.successForwardUrl("/account/generate-user-session").permitAll());
			return http.build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

}
