package chat_room;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@SpringBootTest(classes = vn.tritin.WebHoatHinh.WebHoatHinhApplication.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class ChatRoomTest {

	@Autowired
	private ChatRoomService chatRoomService;

	// Tạo mới một ChatRoom
	@Test
	public void addChatRoom() {
		ChatRoom chatRoom = new ChatRoom("Demo Chat Room 2");
		chatRoomService.saveAndFlush(chatRoom);
		chatRoomService.updateList();
	}
}
