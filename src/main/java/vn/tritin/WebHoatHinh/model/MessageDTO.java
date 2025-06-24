package vn.tritin.WebHoatHinh.model;

import java.sql.Date;

public class MessageDTO {
	private String userId, message, chatRoomId;
	private Date dateSent;
	private boolean changeRoom;

	public MessageDTO() {
	}

	public MessageDTO(String userId, String message, String chatRoomId, Date dateSent, boolean changeRoom) {
		this.userId = userId;
		this.message = message;
		this.chatRoomId = chatRoomId;
		this.dateSent = dateSent;
		this.changeRoom = changeRoom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public boolean isChangeRoom() {
		return changeRoom;
	}

	public void setChangeRoom(boolean changeRoom) {
		this.changeRoom = changeRoom;
	}

	@Override
	public String toString() {
		return "MessageDTO [userId=" + userId + ", message=" + message + ", chatRoomId=" + chatRoomId + ", dateSent="
				+ dateSent + ", changeRoom=" + changeRoom + "]";
	}

}
