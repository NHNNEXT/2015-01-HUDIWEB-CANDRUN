package candrun.email;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailService {
	
	Session session;
	
	public EmailService() {
		session = makeSession();
	}
	
	
	protected Session makeSession(){
	
		final String username = Config.GMAIL_ADRESS;
		final String password = Config.GMAIL_PW;
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		return session;
	}

	public void sendEmail(String requestClass, String ...contents){
	
		MsgWriter msgWriter;
		
		if(requestClass.equals("SignUpController")){
			String verifyKey = contents[0];
			String emailAddress = contents[1];
			
			msgWriter = new CertifMsgWriter();
			msgWriter.setText(verifyKey);
			msgWriter.writeMsg(session,emailAddress);
		}
			
 	}
}
 
