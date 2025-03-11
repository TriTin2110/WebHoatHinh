package vn.tritin.WebHoatHinh.service.util;

import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
	public static String encode(String text) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(text);
	}

	public static String encode(int number) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(String.valueOf(number));
	}

	public static boolean check(String text, String textEncrypted) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.matches(text, textEncrypted);
	}

	public static String base64Encode(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}

	public static String base64Encode(int text) {
		return Base64.getEncoder().encodeToString(String.valueOf(text).getBytes());
	}

}
