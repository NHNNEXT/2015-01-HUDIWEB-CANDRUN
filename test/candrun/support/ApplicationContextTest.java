package candrun.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ApplicationContextTest {
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationContextTest.class);

	@Autowired
	private DataSource dataSource;

	@Resource(name="mailSender")
	private JavaMailSender mailSender;
	
	@Autowired
	private SimpleMailMessage mailMessage;

	@Test
	public void dataSource() {
		assertNotNull(dataSource);
	}

	@Test
	public void mailSender() {
		assertNotNull(mailSender);
		logger.debug("Host: {}", ((JavaMailSenderImpl) mailSender).getHost());
		logger.debug("Properties: {}",
				((JavaMailSenderImpl) mailSender).getJavaMailProperties());
	}
	
	@Test
	public void mailMessage() throws Exception {
		assertNotNull(mailMessage);
		logger.debug("From: {}", mailMessage.getFrom());
		logger.debug("Subject: {}", mailMessage.getSubject());
		logger.debug("Text: {}", mailMessage.getText());
	}
}
