package candrun.service;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import candrun.support.enums.Security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SecurityService {
	private static final Logger logger = LoggerFactory
			.getLogger(SecurityService.class);

	private SecurityService() {
	};

	public static final void setRSA(HttpSession session, Model model)
			throws GeneralSecurityException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		String pubKey = Base64.encode(publicKey.getEncoded());
		String priKey = Base64.encode(privateKey.getEncoded());

		logger.debug("PublicKey:" + pubKey);
		logger.debug("PrivateKey:" + priKey);

		session.setAttribute(Security.RSA_PRI_KEY.getValue(), privateKey);
		model.addAttribute(Security.RSA_PUB_KEY.getValue(), pubKey);
	}

	public static final String decrytRsa(PrivateKey privateKey,
			String encryptedValue) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		byte[] decryptedBytes = null;
		byte[] encryptedBytes = Base64.decode(encryptedValue);

		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, "utf-8");
	}

	public static final String decrypt(String text, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(Base64.decode(text));
		return new String(results, "UTF-8");
	}

	public static final String encrypt(String text, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.encode(results);
	}
}
