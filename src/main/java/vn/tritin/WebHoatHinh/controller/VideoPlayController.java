package vn.tritin.WebHoatHinh.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoPlayController {
	@Value("${path.video}")
	private String path;
	private final int MAX_CHUNK_MB = 5;

	@GetMapping("/{filename}")
	public ResponseEntity<Resource> showVideo(@PathVariable("filename") String fileName,
			@RequestHeader(value = "range", required = false) String header) {
		File file = new File(path + File.separator + fileName);
		if (!file.exists())
			return ResponseEntity.notFound().build();
		else {
			long fileSize = file.length();
			long start = 0;
			long end = fileSize - 1;
			long maxChunk = MAX_CHUNK_MB * 1024 * 1024; // 5MB (for each time server sending)
			if (header != null && header.startsWith("bytes=")) { // checking starting byte
				start = Long.parseLong(header.substring(6).split("-")[0]);
			}
			end = start + maxChunk - 1;
			if (end >= fileSize)
				end = fileSize - 1;
			long contentLength = end - start;
			try {
				byte[] contentByte = new byte[(int) contentLength];
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(start); // begin reading from start value
				raf.readFully(contentByte);
				raf.close();
				ByteArrayResource bar = new ByteArrayResource(contentByte);
				return ResponseEntity.status((header == null) ? 200 : 206).header(HttpHeaders.CONTENT_TYPE, "video/mp4")
						.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
						.header(HttpHeaders.ACCEPT_RANGES, "bytes")
						.header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize).body(bar);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ResponseEntity.badRequest().build();
		}
	}
}
