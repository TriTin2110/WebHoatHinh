package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class VideoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Video không tồn tại!";

	public VideoNotFoundException() {
		super(MESSAGE);
	}
}
