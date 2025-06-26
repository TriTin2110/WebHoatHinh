package vn.tritin.WebHoatHinh.socket;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.entity.Message;
import vn.tritin.WebHoatHinh.model.MessageDTO;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.ChatRoomService;
import vn.tritin.WebHoatHinh.thread.ChatRoomThread;
import vn.tritin.WebHoatHinh.util.StringHandler;

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
	private StringHandler stringHandler;

	public ChatRoomSocket() {
		stringHandler = new StringHandler();
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		MessageDTO messageDTO = getMessageDTO(message);
		String accountId = messageDTO.getUserId();
		String chatRoomId = messageDTO.getChatRoomId();
		String userMessage = messageDTO.getMessage();
		boolean change = messageDTO.isChangeRoom();

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

			// Sau đó lấy toàn bộ tin nhắn có trong chat room đó và gửi cho user
			ChatRoom chatRoomRequired = getChatRoomById(chatRoomId);
			ChatRoom previousChatRoom = accountOnChatRoom.replace(account, chatRoomRequired);
			if (previousChatRoom == null) {
				accountOnChatRoom.put(account, chatRoomRequired);
			} else {
				removeAccountFromPreviousChatRoom(account, previousChatRoom);
			}
			addUserToRoom(chatRoomRequired, session);
			sendAllMessageFromChatRoomToUser(chatRoomRequired, session);
		} else {
			// Trường hợp người dùng muốn gửi tin nhắn
			// Ta phải lấy được chat room hiện tại của user
			// từ chat room này ta phải lấy danh sách user của chat room
			// lần lượt gửi message cho từng user trong danh sách
			// update current chatRoom vào DB
			ChatRoom currentUserChatRoom = accountOnChatRoom.get(account);
			List<WebSocketSession> accountsInChatRoom = accountsOnChatRoom.get(currentUserChatRoom);
			userMessage = sendMessage(userMessage, accountsInChatRoom, account);
			Message messageEntity = createMessageEntity(userMessage, currentUserChatRoom, account);
			currentUserChatRoom.getMessages().add(messageEntity);
			System.out.println(
					"Room: " + currentUserChatRoom.getId() + " size: " + currentUserChatRoom.getMessages().size());
			ChatRoomThread thread = new ChatRoomThread(chatRoomService, currentUserChatRoom);
			thread.start();
			chatRoomService.updateList();
		}
	}

	private Message createMessageEntity(String userMessage, ChatRoom chatRoomRequired, Account account) {
		// TODO Auto-generated method stub
		Date date = new Date(System.currentTimeMillis());
		StringBuilder idMessage = new StringBuilder((String.valueOf(date.getTime())));
		idMessage.append("-");
		idMessage.append(account.getUsername());
		Message messageEntity = new Message(idMessage.toString(), userMessage, date, account, chatRoomRequired);
		return messageEntity;
	}

	private void addUserToRoom(ChatRoom chatRoomRequired, WebSocketSession user) {
		// TODO Auto-generated method stub
		List<WebSocketSession> users = accountsOnChatRoom.get(chatRoomRequired);
		if (users == null) {
			users = new ArrayList<WebSocketSession>();
			accountsOnChatRoom.put(chatRoomRequired, users);
		}
		users.add(user);
		accountsOnChatRoom.replace(chatRoomRequired, users);
	}

	// Ở đây chúng ta sẽ lấy toàn bộ messages có trong chat room
	// sau đó sẽ gửi message
	// Lưu ý khi đã có tin nhắn trước đó thì tin nhắn này đã được mã hóa
	// nên không cần phải mã hóa lại
	private void sendAllMessageFromChatRoomToUser(ChatRoom chatRoom, WebSocketSession user) {
		List<Message> messages = chatRoom.getMessages();
		Account account = (Account) user.getAttributes().get("account");
		String userId = account.getUsername();
		try {
			String otherUserId = null;
			for (Message message : messages) {
				otherUserId = message.getAccount().getUsername();
				if (userId.equals(otherUserId)) {
					user.sendMessage(new TextMessage(message.getMessage()));
				} else {
					user.sendMessage(new TextMessage(otherUserId + ":" + message.getMessage()));
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Để gửi message cho các user trong chat room hiện tại
	// mã hóa message Base64 (để tránh trùng dấu :)
	// lưu message vào csdl (Thread)
	// ta sẽ duyệt từng user có trong chat room
	// và gửi tin nhắn đến những user có id != id của user thực hiện yêu cầu
	private String sendMessage(String message, List<WebSocketSession> accountsInChatRoom, Account account) {
		// TODO Auto-generated method stub
		String userId = account.getUsername();
		Account otherAccount = null;
		message = stringHandler.base64Encode(message);
		try {
			for (WebSocketSession user : accountsInChatRoom) {
				otherAccount = (Account) user.getAttributes().get("account");
				if (userId.equals(otherAccount.getUsername())) {
					user.sendMessage(new TextMessage(message));
				} else {
					user.sendMessage(new TextMessage(userId + ":" + message));
				}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

	private void removeAccountFromPreviousChatRoom(Account account, ChatRoom previousChatRoom) {
		// TODO Auto-generated method stub
		List<WebSocketSession> accountsOnPreviousChatRoom = accountsOnChatRoom.get(previousChatRoom);
		Account accountInList = null;
		for (WebSocketSession accountOnPreviousChatRoom : accountsOnPreviousChatRoom) {
			accountInList = (Account) accountOnPreviousChatRoom.getAttributes().get("account");
			if (account.getUsername().equals(accountInList.getUsername())) {
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
	// ==> lưu chat room này vào chatRoomAvailable
	// trường hợp còn lại thì trả về
	private ChatRoom getChatRoomById(String chatRoomId) {
		// TODO Auto-generated method stub
		ChatRoom chatRoom = chatRoomAvailable.get(chatRoomId);
		if (chatRoom == null) {
			chatRoom = chatRoomService.selectById(chatRoomId);
			if (chatRoom == null)
				chatRoom = new ChatRoom(chatRoomId);
			chatRoomAvailable.put(chatRoomId, chatRoom);
		}
		return chatRoom;
	}

	// Tìm account thông qua session
	// nếu account này == null => tìm trong db
	private Account getUserAccount(WebSocketSession session, String accountId) {
		// TODO Auto-generated method stub
		Account account = (Account) session.getAttributes().get("account");
		if (account == null) {
			account = accountService.selectAccountByUsername(accountId);
//			if(account == null)
//				throw new AccountNotFoundException()
			session.getAttributes().put("account", account);
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

	// Khi user đóng kết nối ta phải
	// lấy chat room trước đó của user
	// Xóa user đó ra khỏi chat room trước đó accountsOnChatRoom
	// xóa user-chat room ra khỏi accountOnChatRoom
	// Thực hiện việc thông báo
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		Account account = (Account) session.getAttributes().get("account");
		if (account != null) {
			ChatRoom previousChatRoom = accountOnChatRoom.get(account);
			removeAccountFromPreviousChatRoom(account, previousChatRoom);
			accountOnChatRoom.remove(account);
			Logger.getLogger(this.getClass().getName())
					.info("User " + account.getUsername() + " đã thoát ra chat room!");
		}
	}
}
