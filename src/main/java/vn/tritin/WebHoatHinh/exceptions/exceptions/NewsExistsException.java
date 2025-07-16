package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class NewsExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NewsExistsException() {
		super("Tin tức này đã tồn tại!");
	}
}
