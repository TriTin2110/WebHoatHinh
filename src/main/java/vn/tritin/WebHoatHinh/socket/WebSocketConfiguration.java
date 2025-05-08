package vn.tritin.WebHoatHinh.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(getCommentSocket(), "/comment"); // Websocket url for comment
	}

	@Bean
	public CommentSocket getCommentSocket() {
		return new CommentSocket();
	}
}
