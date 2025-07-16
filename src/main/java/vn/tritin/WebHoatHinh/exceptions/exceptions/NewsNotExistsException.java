package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class NewsNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NewsNotExistsException() {
		super("Tin tức này chưa tồn tại!");
	}
}
