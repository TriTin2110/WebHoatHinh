package vn.tritin.WebHoatHinh.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UtilExceptionHandler {
	@ExceptionHandler(exception = { FileNotFoundException.class })
	public String handlingFileNotFoundException(FileNotFoundException ex) {
		return "/exceptions/FileNotFound";
	}
}
