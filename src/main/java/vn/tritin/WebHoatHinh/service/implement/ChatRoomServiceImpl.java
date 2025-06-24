package vn.tritin.WebHoatHinh.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOChatRoom;
import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
	private DAOChatRoom dao;

	@Autowired
	public ChatRoomServiceImpl(DAOChatRoom dao) {
		this.dao = dao;
	}

	@Override
	public ChatRoom selectById(String chatRoomId) {
		// TODO Auto-generated method stub
		Optional<ChatRoom> opt = dao.findById(chatRoomId);
		return (opt.isEmpty()) ? null : opt.get();
	}

}
