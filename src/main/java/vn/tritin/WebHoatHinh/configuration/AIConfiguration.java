package vn.tritin.WebHoatHinh.configuration;

import java.sql.Date;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import redis.clients.jedis.JedisPooled;
import vn.tritin.WebHoatHinh.entity.ChatRoom;
import vn.tritin.WebHoatHinh.service.ChatRoomService;

@Configuration
public class AIConfiguration {
	@Value("classpath:/GuideForAI.txt")
	private Resource resource;
	@Value("Chat-room-AI.webp")
	private String bannerPath;
	private ChatRoomService chatRoomService;

	@Autowired
	public AIConfiguration(ChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@Bean
	public ChatClient createChatClient(ChatClient.Builder builder) {
		ChatMemory memory = MessageWindowChatMemory.builder().maxMessages(5).build();
		if (!chatRoomService.isChatRoomAIAvailable()) {
			ChatRoom chatRoomAI = new ChatRoom("Chat Room AI", "Chat room AI demo", bannerPath,
					new Date(System.currentTimeMillis()));
			chatRoomService.saveAndFlush(chatRoomAI);
		}
		return builder.defaultSystem(resource).defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
				.build();
	}

	@Bean
	public JedisPooled createJedisPooled() {
		return new JedisPooled("redis-14709.crce194.ap-seast-1-1.ec2.redns.redis-cloud.com", 14709, "root",
				"Nguyentritin@123");
	}

	@Bean
	@Primary
	public VectorStore createVectorStore(EmbeddingModel embeddingModel, JedisPooled pool) {
		return RedisVectorStore.builder(pool, embeddingModel).indexName("video")
				.metadataFields(MetadataField.text("id"), MetadataField.text("categories"),
						MetadataField.text("director"), MetadataField.text("language"), MetadataField.numeric("viewer"))
				.initializeSchema(true).build();
	}
}
