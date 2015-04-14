package candrun.mail;

import org.springframework.mail.SimpleMailMessage;

public interface MailBodyWriter {

	String writeBody(SimpleMailMessage mailMessage);
}
