package vn.tritin.WebHoatHinh.controller;

import java.util.List;
import java.util.stream.Collectors;

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
	private final String CHAT_ROOM_AI_ID = "Chat Room AI";

	// redirect user to chat room page
	// must have account and chat room list
	@GetMapping("/chat-room")
	public String accessChatRoomPage(@RequestParam("username") String userId, Model model) {

		List<ChatRoom> chatRooms = chatRoomService.selectAll();

		// Move chat room id to head
		ChatRoom chatRoomAI = chatRooms.stream().filter(o -> CHAT_ROOM_AI_ID.equals(o.getId()))
				.collect(Collectors.toList()).get(0);
		int index = chatRooms.indexOf(chatRoomAI);
		chatRooms.remove(index);
		chatRooms.add(0, chatRoomAI);

		model.addAttribute("userId", userId);
		model.addAttribute("chatRooms", chatRooms);
		return "chat-room/index";
	}

}
