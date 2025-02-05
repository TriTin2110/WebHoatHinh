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

}
