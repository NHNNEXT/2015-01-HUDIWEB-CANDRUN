package candrun.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InvitationMsgWriter implements MsgWriter {

	String subject = "메일 전송 TEST입니다.";
	String text;
	
	@Override
	public void setText(String goalId) {
		text = "getRequestUrl?goalId="+goalId;
	}

	@Override
	public void writeMsg(Session session, String emailAddress) {
		
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("gubagiserver@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailAddress));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		
		} catch  (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
