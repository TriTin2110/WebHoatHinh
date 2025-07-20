package vn.tritin.WebHoatHinh.thread;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import vn.tritin.WebHoatHinh.entity.Account;

public class SendMessageChatRoomThread extends Thread {

	private List<WebSocketSession> allUsers;
	private String chatRoomId;
	private Account otherAccount;
	private String userId;
	private String message;
	private Queue<SendAllMessageForUserThread> sendAllMessageTheads;

	public SendMessageChatRoomThread(List<WebSocketSession> allUsers, String chatRoomId, Account otherAccount,
			String userId, String message) {
		this.allUsers = allUsers;
		this.chatRoomId = chatRoomId;
		this.otherAccount = otherAccount;
		this.userId = userId;
		this.message = message;
		this.sendAllMessageTheads = new LinkedList<SendAllMessageForUserThread>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// Xử lý nếu có nhìu user cùng chat
		// Tạo vòng lập for i; i< allUsers.size; i+=Maximum
		// Tạo thread mỗi thread sẽ gửi tin nhắn đến cho maximum người

		SendAllMessageForUserThread thread = null;
		for (WebSocketSession user : allUsers) {
			thread = (SendAllMessageForUserThread) user.getAttributes().get("sendAllMessageThread");
			if (thread.isAlive()) { // if there is anyone is loading message from chat room
				sendAllMessageTheads.add(thread);
			} else {
				sendMessage(user);
			}
		}

		WebSocketSession user = null;
		while (!sendAllMessageTheads.isEmpty()) {
			if (!sendAllMessageTheads.peek().isAlive()) {
				user = sendAllMessageTheads.remove().getUser();
				sendMessage(user);
			}
		}
	}

	private void sendMessage(WebSocketSession user) {
		try {
			String currentRoom = (String) user.getAttributes().get("currentRoom");
			StringBuilder sendMessage = new StringBuilder();
			if (chatRoomId.equals(currentRoom)) {
				otherAccount = (Account) user.getAttributes().get("account");
				if (userId.equals(otherAccount.getUsername())) {
					user.sendMessage(new TextMessage(message));
				} else {
					sendMessage.append(userId);
					sendMessage.append(":");
					sendMessage.append(message);
					user.sendMessage(new TextMessage(sendMessage.toString()));
				}
			} else {
				sendMessage.append("*");
				sendMessage.append(chatRoomId);
				sendMessage.append("*:");
				sendMessage.append(userId);
				sendMessage.append(":");
				sendMessage.append(message);
				System.out.println("sendMessage " + sendMessage.toString());
				user.sendMessage(new TextMessage(sendMessage.toString()));
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
