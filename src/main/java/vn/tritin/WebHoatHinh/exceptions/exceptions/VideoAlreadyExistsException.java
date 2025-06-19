package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class VideoAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Video đã tồn tại trong DB không thể thêm mới!";

	public VideoAlreadyExistsException() {
		super(MESSAGE);
	}
}
