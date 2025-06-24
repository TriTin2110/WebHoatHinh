package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class ChatRoom extends Content {

	private int numberParticipant;
	private String recentlyMessage;

	@OneToMany(mappedBy = "chatRoom")
	private List<Message> messages;

	public ChatRoom() {
	}

	public ChatRoom(int numberParticipant, String recentlyMessage, List<Message> messages) {
		super();
		this.numberParticipant = numberParticipant;
		this.recentlyMessage = recentlyMessage;
		this.messages = messages;
	}

	public ChatRoom(String id, String description, Date dateUploaded, String banner, int numberParticipant,
			String recentlyMessage, List<Message> messages) {
		super(id, description, dateUploaded, banner);
		this.numberParticipant = numberParticipant;
		this.recentlyMessage = recentlyMessage;
		this.messages = messages;
	}

	public int getNumberParticipant() {
		return numberParticipant;
	}

	public void setNumberParticipant(int numberParticipant) {
		this.numberParticipant = numberParticipant;
	}

	public String getRecentlyMessage() {
		return recentlyMessage;
	}

	public void setRecentlyMessage(String recentlyMessage) {
		this.recentlyMessage = recentlyMessage;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
