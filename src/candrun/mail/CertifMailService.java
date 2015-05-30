package candrun.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public class CertifMailService implements MailService {

	MailContext mailContext;
	Map<String, Object> mailBodyElements = new HashMap<String, Object>();

	public void setMailContext(MailContext mailContext) {
		this.mailContext = mailContext;
	}
	
	
	public void sendMail(String toMail) {
		
		MailBodyWriter writer = new MailBodyWriter() {
			@Override
			public String writeBody(SimpleMailMessage mailMessage) {
				String body = mailMessage.getText()+mailBodyElements.get("key")+"/verify";
				return body;
			}
		};
		mailContext.workWithBodyWriter(writer, toMail);
	}

	@Override
	public void putMailBodyElement(String email) {
		try {
			mailBodyElements.put("key", CryptoUtil.encrypt(email));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
