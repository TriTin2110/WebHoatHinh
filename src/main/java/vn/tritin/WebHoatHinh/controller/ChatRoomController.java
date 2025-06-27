package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService chatRoomService;

	// Chuyển ng dùng đến chat room
	// Ta cần có Account và danh sách tất cả chat room hiện có
	@GetMapping("/chat-room")
	public String accessChatRoomPage(@RequestParam("username") String userId, Model model) {
		List<ChatRoom> chatRooms = chatRoomService.selectAll();
		chatRooms.sort((o1, o2) -> {
			return Long.compare(o2.getDateUploaded().getTime(), o1.getDateUploaded().getTime());
		});
		model.addAttribute("userId", userId);
		model.addAttribute("chatRooms", chatRooms);
		return "chat-room/index";
	}

}
