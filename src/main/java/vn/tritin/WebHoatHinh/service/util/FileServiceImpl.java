package vn.tritin.WebHoatHinh.service.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.thread.DeleteFileThread;
import vn.tritin.WebHoatHinh.util.ImageHandler;

@Service
public class FileServiceImpl implements FileService {

	/*-
	 * Here we will save file user request
	 * if file is image file with those extensions (.png, .jpg, .jpeg) will convert to .webp
	 * File name will contain <Current Time>
	 * Then we will create the file from The MultipartFile and save it
	 * if there is any error we will return null then the controller will stop update other information
	 * */
	public String saveFile(String path, MultipartFile file) {
		String resultPath = file.getOriginalFilename();
		String extension = resultPath.substring(resultPath.lastIndexOf("."), resultPath.length());
		StringBuilder fileName = new StringBuilder(String.valueOf(System.currentTimeMillis()));
		StringBuilder storingPath = new StringBuilder(path + File.separator);

		File pathDir = new File(path);
		if (!pathDir.exists())
			pathDir.mkdirs();
		if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png")) {
			extension = ".webp";
			fileName.append(extension);
			storingPath.append(fileName);
			try {
				ImageHandler.saveAsWebp(file.getInputStream(), storingPath.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else if (extension.equals(".mp4")) {
			fileName.append(extension);
			storingPath.append(fileName);
			try {
				Files.copy(file.getInputStream(), Path.of(storingPath.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileName.toString();
	}

	@Override
	public boolean isFileExists(String path, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(path + fileName);
		return file.exists();
	}

	@Override
	public boolean removeFile(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		try {
			DeleteFileThread thread = new DeleteFileThread(file);
			thread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
