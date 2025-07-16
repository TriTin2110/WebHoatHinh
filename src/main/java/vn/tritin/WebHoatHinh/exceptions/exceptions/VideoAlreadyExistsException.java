package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class VideoAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Video đã tồn tại trong DB không thể thêm mới!";
	private String id;

	public VideoAlreadyExistsException(String id) {
		super(MESSAGE);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
