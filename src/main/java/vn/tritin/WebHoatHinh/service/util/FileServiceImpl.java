package vn.tritin.WebHoatHinh.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

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
		video.setName(file.getOriginalFilename());

		// Get storing path
		String storingPath = path + File.separator + video.getName();

		// Create dir for storing
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();

		// Copy file from client to server
		Files.copy(file.getInputStream(), Paths.get(storingPath));
	}

	/*-
	 * Here we will save the image file with those extensions (.png, .jpg, .jpeg)
	 * File name will contain <The random number> + _ + <Current Time>
	 * Then we will create the file from The MultipartFile and save it at static/img/user-avatar
	 * if there is any error we will return null then the controller will stop update other information
	 * */
	public String saveUserAvatar(String avatarPath, MultipartFile avatarFile) {
		String fileExtension = avatarFile.getOriginalFilename().substring(
				avatarFile.getOriginalFilename().lastIndexOf("."), avatarFile.getOriginalFilename().length());
		if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")) {
			return null;
		}

		String fileName = new Random().nextInt(10) + "_" + System.currentTimeMillis() + fileExtension;
		String path = avatarPath + File.separator + fileName;
		File file = new File(path);
		try {
			file.createNewFile();
			avatarFile.getInputStream().transferTo(new FileOutputStream(file));
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return fileName;
	}
}
