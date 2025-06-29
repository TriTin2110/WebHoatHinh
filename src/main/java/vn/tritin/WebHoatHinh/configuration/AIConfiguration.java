package vn.tritin.WebHoatHinh.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import redis.clients.jedis.JedisPooled;

@Configuration
public class AIConfiguration {
	@Value("classpath:/GuideForAI.txt")
	private Resource resource;

	@Bean
	public ChatClient createChatClient(ChatClient.Builder builder) {
		return builder.defaultSystem(resource).build();
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
				.metadataFields(MetadataField.text("id")) // Tìm kiếm mờ
				.initializeSchema(true).build();
	}
}
