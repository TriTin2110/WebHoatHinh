package vn.tritin.WebHoatHinh.thread;

import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

public class ChatRoomThread extends Thread {
	private ChatRoomService chatRoomService;
	private ChatRoom chatRoom;

	public ChatRoomThread(ChatRoomService chatRoomService, ChatRoom chatRoom) {
		this.chatRoomService = chatRoomService;
		this.chatRoom = chatRoom;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		chatRoomService.saveAndFlush(chatRoom);
	}

}
