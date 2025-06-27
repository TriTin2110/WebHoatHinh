package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {
	@Id
	private String id;
	private Date sentDate;

	@Lob
	private String message;

	@ManyToOne
	@JoinColumn(name = "user")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "chatRoom")
	private ChatRoom chatRoom;

	public Message() {
	}

	public Message(String id, String message, Date sentDate, Account account, ChatRoom chatRoom) {
		this.id = id;
		this.message = message;
		this.sentDate = sentDate;
		this.account = account;
		this.chatRoom = chatRoom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
