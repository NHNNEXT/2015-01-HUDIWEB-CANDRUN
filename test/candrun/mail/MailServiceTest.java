package candrun.mail;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class MailServiceTest {

	@Test
	public void test() {
		MailService mailService = mock(CertifMailService.class);
		mailService.sendMail("test@email.com", "verifyKey");
	}
}
