package vn.tritin.WebHoatHinh.service.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public void postVideo(String path, MultipartFile file) throws IOException;

	public String saveImage(String imagePath, MultipartFile imageFile) throws IOException;

}
