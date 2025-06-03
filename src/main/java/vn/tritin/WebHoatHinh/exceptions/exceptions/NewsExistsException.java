package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class NewsExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NewsExistsException() {
		super("This News's id has already existed in DB!");
	}
}
