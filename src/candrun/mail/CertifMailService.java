package candrun.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class CertifMailService implements MailService{

	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage mailMessage;

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	public void sendMail(String toMail, String verifyKey) {
		
		String fromMail = mailMessage.getFrom();
		String subject = mailMessage.getSubject();
		String body = mailMessage.getText()+"http://localhost:8080/VerifySignUp.cdr?verify_key="+verifyKey;
		
		MimeMessage  mimeMessage = mailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(fromMail);
				helper.setTo(toMail);
				helper.setSubject(subject);
				helper.setText(body);		
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		mailSender.send(mimeMessage);
	}
}
