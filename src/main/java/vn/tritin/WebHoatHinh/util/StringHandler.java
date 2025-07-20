package vn.tritin.WebHoatHinh.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringHandler {

	public String base64Encode(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
	}

	public String base64Decode(String encodeText) {
		return new String(Base64.getDecoder().decode(encodeText.getBytes(StandardCharsets.UTF_8)));
	}
}
