package vn.tritin.WebHoatHinh.service.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public void postVideo(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		// Creating new video entity
		Video video = new Video();
		video.setId(file.getOriginalFilename());

		// Get storing path
		String storingPath = path + File.separator + video.getId();

		// Create dir for storing
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();

		// Copy file from client to server
		Files.copy(file.getInputStream(), Paths.get(storingPath));
	}

	/*-
	 * Here we will save the image file with those extensions (.png, .jpg, .jpeg)
	 * File name will contain <Current Time>
	 * Then we will create the file from The MultipartFile and save it
	 * if there is any error we will return null then the controller will stop update other information
	 * */
	public String saveImage(String imagePath, MultipartFile imageFile) {
		String fileExtension = imageFile.getOriginalFilename()
				.substring(imageFile.getOriginalFilename().lastIndexOf("."), imageFile.getOriginalFilename().length());
		if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")) {
			return null;
		}

		String fileName = System.currentTimeMillis() + fileExtension;

		String storingPath = imagePath + File.separator + fileName;

		File storingFolder = new File(imagePath);
		if (!storingFolder.exists()) {
			storingFolder.mkdirs();
		}
		File img = new File(storingPath);

		try {
			Files.copy(imageFile.getInputStream(), img.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return fileName;
	}
}
