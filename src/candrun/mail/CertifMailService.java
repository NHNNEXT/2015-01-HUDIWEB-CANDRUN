package candrun.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class CertifMailService implements MailService {

	MailContext mailContext;

	public void setMailContext(MailContext mailContext) {
		this.mailContext = mailContext;
	}

	public void sendMail(String toMail, String verifyKey) {

		MailBodyWriter writer = new MailBodyWriter() {
			@Override
			public String writeBody(SimpleMailMessage mailMessage) {
				String body = mailMessage.getText() + "http://localhost:8080/VerifySignUp.cdr?verify_key=" + verifyKey;
				return body;
			}
		};
		mailContext.workWithBodyWriter(writer, toMail);
	}
}
