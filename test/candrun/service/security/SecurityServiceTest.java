package candrun.service.security;

import static org.junit.Assert.*;

import org.junit.Test;

import candrun.service.SecurityService;

public class SecurityServiceTest {

	@Test
	public void testAES() throws Exception {
		String plainText = "asdf@asdf.asdf";
		String cipherText = SecurityService.encrypt(plainText, plainText);
		System.out.println(cipherText);
		assertEquals(plainText, SecurityService.decrypt(cipherText, plainText));
	}

}
