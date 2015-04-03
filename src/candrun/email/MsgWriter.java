package candrun.email;

import javax.mail.Session;

public interface MsgWriter {

	public void setText(String parameter);
	public void writeMsg(Session session, String parameter);
}
