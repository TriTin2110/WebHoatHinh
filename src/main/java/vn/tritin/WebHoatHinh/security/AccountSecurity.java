package vn.tritin.WebHoatHinh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
	public SecurityFilterChain createChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				con -> con.requestMatchers(HttpMethod.GET, "/home").authenticated().anyRequest().permitAll())
				.formLogin(login -> login.loginProcessingUrl("/authenticateTheUser"));
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
