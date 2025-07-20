package vn.tritin.WebHoatHinh.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	private JavaMailSender mailSender;
	private final String FROM = "webhoathinhoffical@gmail.com";
	private final String SUBJECT = "Mã xác thực từ WebHoatHinh";

	@Autowired
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String text) {

		text = "Mã của bạn là: " + text;

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(FROM);
		mailMessage.setTo(to);
		mailMessage.setSubject(SUBJECT);
		mailMessage.setText(text);
		try {
			mailSender.send(mailMessage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
