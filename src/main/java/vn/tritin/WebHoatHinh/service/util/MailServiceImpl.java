package vn.tritin.WebHoatHinh.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	private JavaMailSender mailSender;

	@Autowired
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String text) {
		String from = "nguyenvanmoi1306@gmail.com";
		String subject = "Thử nghiệm mail Spring Boot";
		text = "Mã của bạn là: " + text;

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);

		mailSender.send(mailMessage);
	}
}
