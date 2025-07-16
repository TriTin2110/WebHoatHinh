package vn.tritin.WebHoatHinh.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface ChatRoomAIService {
	public String searchByDescription(@RequestParam("description") String description);
}
