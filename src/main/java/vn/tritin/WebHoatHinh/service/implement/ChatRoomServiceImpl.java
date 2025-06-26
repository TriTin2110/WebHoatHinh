package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

	@Override
	@Cacheable("chatRooms")
	public List<ChatRoom> selectAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	@Transactional
	public synchronized void saveAndFlush(ChatRoom chatRoom) {
		// TODO Auto-generated method stub
		dao.saveAndFlush(chatRoom);
	}

	@Override
	@CachePut("chatRooms")
	public List<ChatRoom> updateList() {
		// TODO Auto-generated method stub
		List<ChatRoom> chatRooms = dao.findAll();
		return chatRooms;
	}

}
