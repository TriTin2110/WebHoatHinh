package vn.tritin.WebHoatHinh.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.tritin.WebHoatHinh.entity.Account;
import vn.tritin.WebHoatHinh.entity.Comment;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.entity.VideoDetail;
import vn.tritin.WebHoatHinh.model.CommentDTO;
import vn.tritin.WebHoatHinh.service.AccountService;
import vn.tritin.WebHoatHinh.service.CommentService;
import vn.tritin.WebHoatHinh.service.VideoAnalystService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.thread.UpdateVideoAnalyst;

@Component
public class CommentSocket extends TextWebSocketHandler {
	private final ObjectMapper om = new ObjectMapper();
	private final Logger log = Logger.getLogger(CommentSocket.class.getName());

	// We need a Map for storing VideoDetailId and users current watching it
	private static Map<String, List<WebSocketSession>> sessions = new HashMap<String, List<WebSocketSession>>();
	@Autowired
	private AccountService accSer;
	@Autowired
	private CommentService comSer;
	@Autowired
	private VideoService videoSer;
	@Autowired
	private VideoAnalystService analystService;

	public CommentSocket() {
		super();
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		String videoDetailId = (String) session.getAttributes().get("videoDetailId");
		if (videoDetailId == null) // When new connection established
			addUserToList(session, message);
		else {
			Comment comment = saveComment(session, message);
			if (comment != null)
				sendMessageForAllUser(session, comment);
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		String videoDetailId = (String) session.getAttributes().get("videoDetailId");
		log.info("User đã thoát!");
		sessions.get(videoDetailId).remove(session);
	}

	private Comment saveComment(WebSocketSession session, WebSocketMessage<?> message) {
		CommentDTO commentDTO = null;
		String videoId = null;
		try {
			commentDTO = om.readValue(message.getPayload().toString(), CommentDTO.class);
			videoId = commentDTO.getVideoDetailId();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Comment comment = createComment(session, commentDTO);
		UpdateVideoAnalyst updateAnalyst = new UpdateVideoAnalyst(videoId, analystService);
		updateAnalyst.start();
		return comSer.save(comment); // Save comment to DB
	}

	private Comment createComment(WebSocketSession session, CommentDTO commentDTO) {
		List<Video> videos = videoSer.findAll();

		Account account = (Account) session.getAttributes().get("account"); // Get the account storing in the session
		if (account == null) {// If in current session user doesn't make any comment then we will select that
								// user
			account = accSer.selectAccountByUsername(commentDTO.getAccountId());
			session.getAttributes().put("account", account);
		}
		VideoDetail videoDetail = null;
		out: for (Video video : videos) {
			if (video.getId().equals(commentDTO.getVideoDetailId())) {
				videoDetail = video.getVideoDetail();
				break out;
			}
		}

		Date date = commentDTO.getDate();
		StringBuilder idComment = new StringBuilder();
		idComment.append(date.getTime());
		idComment.append("-");
		idComment.append(account.getUsername());
		Comment comment = new Comment(idComment.toString(), commentDTO.getText(), date, account.getUser(), videoDetail);
		return comment;
	}

	private void addUserToList(WebSocketSession session, WebSocketMessage<?> message) {
		String videoDetailId = null;
		videoDetailId = message.getPayload().toString();
		if (sessions.get(videoDetailId) == null) { // If the list doesn't contain this video
			sessions.put(videoDetailId, new ArrayList<WebSocketSession>());
		}
		List<WebSocketSession> list = sessions.get(videoDetailId);
		list.add(session);
		sessions.replace(videoDetailId, list); // Update list user by videoDetailId
		session.getAttributes().put("videoDetailId", videoDetailId); // Save video id for user
		log.info("User đã vào!");
	}

	private void sendMessageForAllUser(WebSocketSession userCommented, Comment comment) {
		Account account = (Account) userCommented.getAttributes().get("account");
		String html = "<div class = \"comment\">" + "<img src=\"/img/user-avatar/" + account.getUser().getAvatar()
				+ "\" alt=\"Không có ảnh\">" + "<div>" + "<div class=\"comment-content\">" + comment.getContent()
				+ "</div>" + "<div class=\"comment-actions\">" + "		<span>Thích</span>"
				+ "     <span>Phản hồi</span>" + "     <span>1 phút trước</span>" + "</div>" + "</div>" + "</div>";
		synchronized (sessions) {
			// Get all user at the same movie
			List<WebSocketSession> webSocketSessions = sessions.get(comment.getVideoDetail().getId());
			for (WebSocketSession userInList : webSocketSessions) {
				if (userInList.isOpen() && userInList != userCommented) {
					try {
						userInList.sendMessage(new TextMessage(html));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
