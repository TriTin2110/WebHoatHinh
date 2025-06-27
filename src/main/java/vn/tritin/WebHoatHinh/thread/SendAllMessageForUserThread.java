package vn.tritin.WebHoatHinh.thread;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import vn.tritin.WebHoatHinh.entity.Message;

public class SendAllMessageForUserThread extends Thread {

	private List<Message> messages;
	private String userId;
	private WebSocketSession user;

	public SendAllMessageForUserThread(List<Message> messages, String userId, WebSocketSession user) {
		this.messages = messages;
		this.userId = userId;
		this.user = user;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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

	public WebSocketSession getUser() {
		return user;
	}

	public void setUser(WebSocketSession user) {
		this.user = user;
	}

}
