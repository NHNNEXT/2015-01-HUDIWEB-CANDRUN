package candrun.mail;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class SHA256Encrypt {
	public static String encrypt(String value) {
		final String hashed = Hashing.sha256().hashString(value, Charsets.UTF_8).toString();
		return hashed;
	}
}
