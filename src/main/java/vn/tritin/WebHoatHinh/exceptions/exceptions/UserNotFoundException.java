package vn.tritin.WebHoatHinh.exceptions.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Đăng nhập không thành công!");
	}
}
