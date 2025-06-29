package vn.tritin.WebHoatHinh.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageHandler {
	public static void saveAsWebp(InputStream inputStream, String path) {
		try {
			ImageIO.scanForPlugins();
			BufferedImage buffer = ImageIO.read(inputStream);
			File outputFile = new File(path);
			if (!outputFile.exists())
				outputFile.createNewFile();
			ImageIO.write(buffer, "webp", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
