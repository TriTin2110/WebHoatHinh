package image;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import vn.tritin.WebHoatHinh.util.ImageHandler;

public class ImageConvert {
	@Test
	public void convertJPGToWebp() {
		try {
			File file = new File("C:\\Users\\DELL\\Pictures\\b058814d75b5cbeb92a4.jpg");
			InputStream input = new FileInputStream(file);
			assertDoesNotThrow(() -> ImageHandler.saveAsWebp(input, "D:\\Test.webp"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
