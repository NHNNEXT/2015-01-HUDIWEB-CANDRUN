package candrun.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public class CertifMailService implements MailService {
	
	MailContext mailContext;
	Map<String, Object> mailBodyElements = new HashMap<String, Object>();

	public CertifMailService(MailContext mailContext) {
		this.mailContext = mailContext;
	}
	
	public void setMailReceiver(String mailReceiver){
		mailContext.setMailReceiver(mailReceiver);
	}
	
	@Override
	public void run() {
		MailBodyWriter writer = new MailBodyWriter() {
			@Override
			public String writeBody(SimpleMailMessage mailMessage) {
				String body = mailMessage.getText()+mailBodyElements.get("key")+"/verify";
				return body;
			}
		};
		mailContext.workWithBodyWriter(writer);
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
