package vn.tritin.WebHoatHinh.entity;

public class ChatMessage {
	private String sender, content, timeStamp;

	public ChatMessage() {
		super();
	}

	public ChatMessage(String sender, String content) {
		super();
		this.sender = sender;
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
