package vn.tritin.WebHoatHinh.exceptions.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.tritin.WebHoatHinh.exceptions.exceptions.FileNotFoundException;

@ControllerAdvice
public class UtilExceptionHandler {
	@ExceptionHandler(exception = { FileNotFoundException.class })
	public String handlingFileNotFoundException(FileNotFoundException ex) {
		return "/exceptions/FileNotFound";
	}
}
