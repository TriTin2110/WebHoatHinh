package vn.tritin.WebHoatHinh.controller.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@Controller
@RequestMapping("/admin")
public class ChatRoomControllerManager {

	@Autowired
	private ChatRoomService chatRoomService;

	@Value("${path.chat-room-avatar}")
	private String path;

	@PostMapping("/create-chat-room")
	public ResponseEntity<Map<String, Boolean>> insertChatRoom(@RequestParam("id") String id,
			@RequestParam("description") String description, @RequestParam("banner") MultipartFile banner) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		ChatRoom chatRoom = chatRoomService.selectById(id);
		if (chatRoom != null) {
			result.put("result", false);
			return ResponseEntity.ok(result);
		}
		chatRoom = createData(id, description, banner);
		chatRoomService.saveAndFlush(chatRoom);
		chatRoomService.updateList();

		result.put("result", true);
		return ResponseEntity.ok(result);
	}

	private ChatRoom createData(String id, String description, MultipartFile banner) {
		// TODO Auto-generated method stub
		Date date = new Date(System.currentTimeMillis());
		File file = saveFile(id, banner);
		return new ChatRoom(id, description, file.getName(), date);
	}

	private File saveFile(String id, MultipartFile banner) {
		// TODO Auto-generated method stub
		String fileExtend = banner.getOriginalFilename().substring(banner.getOriginalFilename().indexOf("."),
				banner.getOriginalFilename().length());
		long fileName = System.currentTimeMillis();
		if (fileExtend.equals(".png") || fileExtend.equals(".jpg") || fileExtend.equals(".jpeg")) {
			fileExtend = ".webp";
		}
		File file = new File(path + File.separator + fileName + fileExtend);
		if (!file.exists()) {
			try {
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			byte[] bytes = banner.getInputStream().readAllBytes();
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

}
