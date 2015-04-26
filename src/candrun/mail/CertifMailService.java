package candrun.mail;

import org.springframework.mail.SimpleMailMessage;

public class CertifMailService implements MailService {

	MailContext mailContext;

	public void setMailContext(MailContext mailContext) {
		this.mailContext = mailContext;
	}

	public void sendMail(String toMail, String key) {

		MailBodyWriter writer = new MailBodyWriter() {
			@Override
			public String writeBody(SimpleMailMessage mailMessage) {
				String body = mailMessage.getText() + "http://localhost:8080/users/"+key+"/verify";
				return body;
			}
		};
		mailContext.workWithBodyWriter(writer, toMail);
	}
}
