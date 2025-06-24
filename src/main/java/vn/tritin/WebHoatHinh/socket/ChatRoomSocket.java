package vn.tritin.WebHoatHinh.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.model.MessageDTO;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@Component
public class ChatRoomSocket extends TextWebSocketHandler {
	private ObjectMapper om = new ObjectMapper();
	@Autowired
	private AccountService accountService;
	@Autowired
	private ChatRoomService chatRoomService;

	private static Map<Account, ChatRoom> accountOnChatRoom = new HashMap<Account, ChatRoom>();
	private static Map<String, ChatRoom> chatRoomAvailable = new HashMap<String, ChatRoom>();
	private static Map<ChatRoom, List<WebSocketSession>> accountsOnChatRoom = new HashMap<ChatRoom, List<WebSocketSession>>();

	public ChatRoomSocket() {
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		MessageDTO messageDTO = getMessageDTO(message);
		String accountId = messageDTO.getUserId();
		String chatRoomId = messageDTO.getChatRoomId();
		String userMessage = messageDTO.getMessage();
		boolean change = messageDTO.isChangeRoom();
		ChatRoom chatRoomRequired = getChatRoomById(chatRoomId);
		Account account = getUserAccount(session, accountId);
		if (change) // mean user join a chat room
		{
			// Lấy account
			// lấy chat room trước đó của account thông qua map<Account, ChatRoom>
			// nếu chat room trước đó tồn tại
			// => thay thế nó bằng chat room ng dùng yêu cầu
			// nếu chat room trước đó CHƯA tồn tại
			// => thêm chat room người dùng yêu cầu vào map<Account, ChatRoom>

			/*- ==> Cập nhật danh sách user cho chat room Map<ChatRoom, List<WebSocketSession>>*/
			// Nếu chat room trước đó của user tồn tại
			// => cập nhật cả 2 chat room (trước đó và chat room yêu cầu)
			// nếu chat room trước đó không tồn tại
			// => cập nhật chat room yêu cầu
			ChatRoom previousChatRoom = accountOnChatRoom.get(account);

			if (previousChatRoom == null) {
				accountOnChatRoom.put(account, chatRoomRequired);
			} else {
				accountOnChatRoom.replace(account, chatRoomRequired);
				removeAccountFromPreviousChatRoom(account, previousChatRoom);
			}
			accountsOnChatRoom.get(chatRoomRequired).add(session);
			session.getAttributes().put("name", account.getUsername());
		} else {
			// Trường hợp người dùng muốn gửi tin nhắn
			// Ta phải lấy được chat room hiện tại của user
			// từ chat room này ta phải lấy danh sách user của chat room
			// lần lượt gửi message cho từng user trong danh sách
			List<WebSocketSession> accountsInChatRoom = accountsOnChatRoom.get(chatRoomRequired);
			sendMessage(userMessage, accountsInChatRoom, account);
		}
	}

	// Để gửi message cho các user trong chat room hiện tại
	// ta sẽ duyệt từng user có trong chat room
	// và gửi tin nhắn đến những user có id != id của user thực hiện yêu cầu
	private void sendMessage(String message, List<WebSocketSession> accountsInChatRoom, Account account) {
		// TODO Auto-generated method stub
		for (WebSocketSession accountInChatRoom : accountsInChatRoom) {
			if (account.getUsername().equals(accountInChatRoom.getAttributes().get("name"))) {
				try {
					accountInChatRoom.sendMessage(new TextMessage(message.getBytes()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private void removeAccountFromPreviousChatRoom(Account account, ChatRoom previousChatRoom) {
		// TODO Auto-generated method stub
		List<WebSocketSession> accountsOnPreviousChatRoom = accountsOnChatRoom.get(previousChatRoom);
		for (WebSocketSession accountOnPreviousChatRoom : accountsOnPreviousChatRoom) {
			if (account.getUsername().equals(accountOnPreviousChatRoom.getAttributes().get("name"))) {
				accountsOnPreviousChatRoom.remove(accountOnPreviousChatRoom);
				break;
			}
		}
		accountsOnChatRoom.replace(previousChatRoom, accountsOnPreviousChatRoom);
	}

	// Để có thể lấy được chat room ra thì ta cần có chatroomId
	// chúng ta sẽ lấy chat room này từ map<chatRoomId, ChatRoom>
	// nếu chatRoom lấy ra == null
	// phải lấy từ DB
	// nếu lấy từ DB = null => tạo mới
	// trường hợp còn lại thì trả về
	private ChatRoom getChatRoomById(String chatRoomId) {
		// TODO Auto-generated method stub
		ChatRoom chatRoom = chatRoomAvailable.get(chatRoomId);
		if (chatRoom == null) {
			chatRoom = chatRoomService.selectById(chatRoomId);
			if (chatRoom == null)
				chatRoom = new ChatRoom();
		}
		return chatRoom;
	}

	// Tìm account thông qua session
	// nếu account này == null => tìm trong db
	private Account getUserAccount(WebSocketSession session, String accountId) {
		// TODO Auto-generated method stub
		Account account = (Account) session.getAttributes().get(accountId);
		if (account == null) {
			account = accountService.selectAccountByUsername(accountId);
//			if(account == null)
//				throw new AccountNotFoundException()
		}

		return account;
	}

	private MessageDTO getMessageDTO(WebSocketMessage<?> message) {
		try {
			MessageDTO messageDTO = om.readValue(message.getPayload().toString(), MessageDTO.class);
			return messageDTO;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
