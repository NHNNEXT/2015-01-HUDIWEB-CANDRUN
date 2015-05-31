package candrun.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailContext {

	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage mailMessage;
	private String mailReceiver;
	
	public MailContext(JavaMailSenderImpl mailSender, SimpleMailMessage mailMessage) {
		this.mailSender = mailSender;
		this.mailMessage = mailMessage;
	}
	
	public void workWithBodyWriter(MailBodyWriter writer) {
		String fromMail = mailMessage.getFrom();
		String subject = mailMessage.getSubject();
		String body = writer.writeBody(mailMessage);

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(fromMail);
			helper.setTo(mailReceiver);
			helper.setSubject(subject);
			helper.setText(body);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailSender.send(mimeMessage);
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}
}