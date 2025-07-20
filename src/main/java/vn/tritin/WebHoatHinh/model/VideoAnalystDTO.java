package vn.tritin.WebHoatHinh.model;

public class VideoAnalystDTO {
	private String videoId, userId;
	private long timeBegin, timeEnd;

	public VideoAnalystDTO() {
	}

	public VideoAnalystDTO(String videoId, String userId, long timeBegin, long timeEnd) {
		this.videoId = videoId;
		this.userId = userId;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(long timeBegin) {
		this.timeBegin = timeBegin;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "VideoAnalystDTO [videoId=" + videoId + ", userId=" + userId + ", timeBegin=" + timeBegin + ", timeEnd="
				+ timeEnd + "]";
	}

}
