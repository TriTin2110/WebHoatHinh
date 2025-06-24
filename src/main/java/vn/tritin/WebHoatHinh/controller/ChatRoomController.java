package vn.tritin.WebHoatHinh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatRoomController {

	@GetMapping("/chat-room")
	public String accessChatRoomPage() {
		return "chat-room/index";
	}
}
