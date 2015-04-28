package candrun.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class IoService {
	private IoService() {
	};

	public static final void saveMultipartFile(MultipartFile file,
			String pathName) throws IOException {
		byte[] bytes = file.getBytes();
		File tempFile = new File(pathName);
		FileOutputStream fos = new FileOutputStream(tempFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(bytes);
		bos.close();
		fos.close();
	}

	public static final String makePicPathName(String email, String extension,
			HttpServletRequest req) {
		String filePath = req.getServletContext().getRealPath("/");
		return filePath + "/img/pics/" + email + extension;
	}
}
