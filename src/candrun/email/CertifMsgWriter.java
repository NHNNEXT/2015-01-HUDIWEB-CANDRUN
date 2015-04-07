package candrun.email;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CertifMsgWriter implements MsgWriter {

	String subject = "구바기 회원 인증 해주세요.";
	String text;	
	
	@Override
	public void setText(String verifyKey) {
		text = "http://localhost:8080/VerifySignUp.cdr?verify_key="+verifyKey;
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