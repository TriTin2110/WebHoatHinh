package vn.tritin.WebHoatHinh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication(exclude = { RedisRepositoriesAutoConfiguration.class, RedisAutoConfiguration.class })

// (exclude = SecurityAutoConfiguration.class) // Tạm ngừng
// Spring
// Security
public class WebHoatHinhApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHoatHinhApplication.class, args);
	}
}
