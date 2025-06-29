package video;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;

import org.junit.jupiter.api.Test;

import vn.tritin.WebHoatHinh.util.video.VideoDuration;

public class VideoDurationTest {
	@Test
	public void getVideoDuration() {
		File file = new File("D:\\Code\\JAVA\\Video\\Core\\Ban đa thât sư hiêu vê Class va Object chưa-.mp4");
		VideoDuration videoDuration = new VideoDuration();
		assertDoesNotThrow(() -> videoDuration.getDuration(file));
	}
}
