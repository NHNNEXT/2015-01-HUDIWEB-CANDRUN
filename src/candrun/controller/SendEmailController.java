package candrun.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import candrun.email.Config;
import candrun.email.InvitationMsgWriter;
import candrun.email.MsgWriter;

@WebServlet("/sendEmail.cdr")
public class SendEmailController extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
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
		

		String requestor = req.getParameter("requestor");
		MsgWriter msgWriter;
		
		if(requestor.equals("/sendEmail.jsp")){
			msgWriter = new InvitationMsgWriter();
			msgWriter.setText(req.getParameter("goal_id"));
			msgWriter.writeMsg(session,req.getParameter("email_address"));
		}
		
 	}
}
 
