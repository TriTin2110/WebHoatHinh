package vn.tritin.WebHoatHinh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AccountSecurity {
	@Bean
	public SecurityFilterChain createChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(con -> con.requestMatchers(HttpMethod.GET).permitAll()
				.requestMatchers(HttpMethod.POST, "/create-user").permitAll());
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
