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
import vn.tritin.WebHoatHinh.service.ChatRoomAIService;
import vn.tritin.WebHoatHinh.service.ChatRoomService;
import vn.tritin.WebHoatHinh.thread.SendAllMessageForUserThread;
import vn.tritin.WebHoatHinh.thread.SendMessageChatRoomThread;
import vn.tritin.WebHoatHinh.thread.UpdateChatRoomThread;
import vn.tritin.WebHoatHinh.util.StringHandler;

@Component
public class ChatRoomSocket extends TextWebSocketHandler {
	private ObjectMapper om = new ObjectMapper();
	@Autowired
	private AccountService accountService;
	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatRoomAIService chatRoomAIService;
	private static Map<Account, ChatRoom> accountOnChatRoom = new HashMap<Account, ChatRoom>();
	private static Map<String, ChatRoom> chatRoomAvailable = new HashMap<String, ChatRoom>();
	private static List<WebSocketSession> allUsers = new ArrayList<WebSocketSession>();

	private final int NUMBER_USER_SEND = 10;
	private final String CHAT_ROOM_AI_ID = "Chat Room AI";

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
			}
			if (session.getAttributes().replace("currentRoom", chatRoomRequired.getId()) == null)
				session.getAttributes().put("currentRoom", chatRoomRequired.getId());
			addUserToRoom(session);
			if (CHAT_ROOM_AI_ID.equals(chatRoomRequired.getId())) {
				String rawMessage = "Xin chào bạn đã đến với " + CHAT_ROOM_AI_ID
						+ " tôi ở đây để hỗ trợ bạn tìm kiếm phim!";
				sendChatBotMessageToUser(rawMessage, session);
			} else
				sendAllMessageFromChatRoomToUser(chatRoomRequired, session);
		} else {
			// Trường hợp người dùng muốn gửi tin nhắn
			// Ta phải lấy được chat room hiện tại của user
			// từ chat room này ta phải lấy danh sách user của chat room
			// lần lượt gửi message cho từng user trong danh sách
			// update current chatRoom vào DB
			ChatRoom currentUserChatRoom = accountOnChatRoom.get(account);
			if (CHAT_ROOM_AI_ID.equals(currentUserChatRoom.getId())) {
				sendMessageToChatRoomAI(userMessage, session);
			} else {
				userMessage = sendMessage(userMessage, account, currentUserChatRoom.getId());
				currentUserChatRoom.setRecentlyMessage(userMessage);
				Message messageEntity = createMessageEntity(userMessage, currentUserChatRoom, account);
				currentUserChatRoom.getMessages().add(messageEntity);
				updateChatRoom(currentUserChatRoom);
			}

		}
	}

	private void updateChatRoom(ChatRoom currentUserChatRoom) {
		// TODO Auto-generated method stub
		UpdateChatRoomThread thread = new UpdateChatRoomThread(chatRoomService, currentUserChatRoom);
		thread.start();
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

	private void addUserToRoom(WebSocketSession user) {
		// TODO Auto-generated method stub
		if (!allUsers.contains(user)) {
			allUsers.add(user);
		}
	}

	// Ở đây chúng ta sẽ lấy toàn bộ messages có trong chat room
	// sau đó sẽ gửi message
	// Lưu ý khi đã có tin nhắn trước đó thì tin nhắn này đã được mã hóa
	// nên không cần phải mã hóa lại
	private void sendAllMessageFromChatRoomToUser(ChatRoom chatRoom, WebSocketSession user) {
		List<Message> messages = new ArrayList<Message>();
		messages.addAll(chatRoom.getMessages());
		Account account = (Account) user.getAttributes().get("account");
		String userId = account.getUsername();
		SendAllMessageForUserThread thread = new SendAllMessageForUserThread(messages, userId, user);
		thread.start();
		user.getAttributes().put("sendAllMessageThread", thread);
	}

	// Để gửi message cho các user trong chat room hiện tại
	// mã hóa message Base64 (để tránh trùng dấu :)
	// lưu message vào csdl (Thread)
	// ta sẽ duyệt từng user có trong chat room
	// và gửi tin nhắn đến những user có id != id của user thực hiện yêu cầu
	private String sendMessage(String message, Account account, String chatRoomId) {
		// TODO Auto-generated method stub
		String userId = account.getUsername();
		Account otherAccount = null;
		message = stringHandler.base64Encode(message);

		int count = 0;
		int temp = 0;
		while (true) {
			temp = count + NUMBER_USER_SEND;
			if (temp > allUsers.size()) {
				SendMessageChatRoomThread thread = new SendMessageChatRoomThread(
						allUsers.subList(count, allUsers.size()), chatRoomId, otherAccount, userId, message);
				thread.start();
				break;
			} else {
				SendMessageChatRoomThread thread = new SendMessageChatRoomThread(allUsers.subList(count, temp),
						chatRoomId, otherAccount, userId, message);
				thread.start();
				count += NUMBER_USER_SEND;
			}
		}
		return message;
	}

	// Gui yeu cau cho user
	// Gui cau hoi len ChatRoomAIService
	// Ma hoa cau tra loi
	// hien cho user
	private String sendMessageToChatRoomAI(String message, WebSocketSession user) {
		// TODO Auto-generated method stub
		try {
			// send owner message
			String rawMessage = message;
			message = stringHandler.base64Encode(message);
			user.sendMessage(new TextMessage(message));

			// send chat response message
			message = chatRoomAIService.searchByDescription(rawMessage);
			sendChatBotMessageToUser(message, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	private void sendChatBotMessageToUser(String message, WebSocketSession user) {
		message = stringHandler.base64Encode(message);
		StringBuilder sendMessage = new StringBuilder();
		sendMessage.append(CHAT_ROOM_AI_ID);
		sendMessage.append(":");
		sendMessage.append(message);
		try {
			Thread.sleep(3000);
			user.sendMessage(new TextMessage(sendMessage.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			allUsers.remove(session);
			accountOnChatRoom.remove(account);
			Logger.getLogger(this.getClass().getName())
					.info("User " + account.getUsername() + " đã thoát ra chat room!");
		}
	}
}
