package candrun.mail;

public interface MailService extends Runnable {

	public void setMailReceiver(String email);
	public void putMailBodyElement(String element);
}
