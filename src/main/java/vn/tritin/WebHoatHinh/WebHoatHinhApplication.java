package vn.tritin.WebHoatHinh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Tạm ngừng Spring Security
public class WebHoatHinhApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHoatHinhApplication.class, args);
	}
}
