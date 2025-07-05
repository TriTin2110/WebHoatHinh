package vn.tritin.WebHoatHinh.service.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String saveFile(String path, MultipartFile file);

	public boolean isFileExists(String path, String fileName);
}
