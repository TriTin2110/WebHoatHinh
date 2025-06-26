package vn.tritin.WebHoatHinh.service;

import java.util.List;

import vn.tritin.WebHoatHinh.entity.ChatRoom;

public interface ChatRoomService {
	public ChatRoom selectById(String chatRoomId);

	public List<ChatRoom> selectAll();

	public void saveAndFlush(ChatRoom chatRoom);

	public List<ChatRoom> updateList();
}
