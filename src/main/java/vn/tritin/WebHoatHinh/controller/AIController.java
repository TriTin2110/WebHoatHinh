package vn.tritin.WebHoatHinh.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.service.VectorStoreService;

@RestController
@RequestMapping("/ai")
public class AIController {
	private final String DESCRIPTION_FOR_GET_VIDEO = "Tìm các video có id nằm trong danh sách {ids}";
	@Autowired
	private VectorStoreService vectorStoreService;
	@Autowired
	private List<ToolCallback> toolAIConfiguration;
	@Autowired
	private ChatClient chatClient;
	private ChatClient chatClient2;

	public AIController(ChatClient.Builder chatClient2) {
		this.chatClient2 = chatClient2.build();
	}

	@PostMapping("")
	public void insertData(@RequestBody VectorStoreDTO data) {
		vectorStoreService.insertData(data);
	}

	@PostMapping("/update")
	public void updateData(@RequestBody VectorStoreDTO data) {
		vectorStoreService.updateData(data);
	}

	@GetMapping("/remove/{id}")
	public void updateData(@PathVariable("id") String id) {
		vectorStoreService.deleteData(id);
	}

	@GetMapping("")
	public String searchByDescription(@RequestParam("description") String description) {
		List<String> ids = vectorStoreService.getDataByDescription(description).stream()
				.map(o -> o.getMetadata().get("id").toString()).toList();
		if (ids.isEmpty()) {
			return chatClient.prompt("Không có phim nào phù hợp").call().content();
		} else {
			ChatMemory memory = MessageWindowChatMemory.builder().maxMessages(10).build();
			String result = chatClient.prompt().user(o -> {
				o.text(DESCRIPTION_FOR_GET_VIDEO).param("ids", ids);
			}).advisors(MessageChatMemoryAdvisor.builder(memory).build()).toolCallbacks(toolAIConfiguration).call()
					.content();
			return (result.isBlank()) ? "Không có phim nào phù hợp!" : result;
		}
//		String result = chatClient.prompt(description).call().chatResponse().getResult().getOutput().getText();
//		return result;
	}
}
