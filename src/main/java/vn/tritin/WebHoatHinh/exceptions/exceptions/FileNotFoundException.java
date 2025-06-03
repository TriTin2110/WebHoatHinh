package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class FileNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileNotFoundException() {
		super("Không tìm thấy file!");
	}
}
