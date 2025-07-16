package video;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import vn.tritin.WebHoatHinh.WebHoatHinhApplication;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.service.VideoService;

@SpringBootTest(classes = { WebHoatHinhApplication.class })
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DeleteAll {
	@Autowired
	private VideoService videoService;
	String[] idList = { "Avatar 3", "Superman", "Captain America: Brave New World", "The Fantastic Four",
			"Mission: Impossible 8", "The Batman - Part II", "Gladiator 2", "Wicked: Part One", "Joker: Folie à Deux",
			"Mufasa: The Lion King", "Spider-Man: Beyond the Spider-Verse", "Inside Out 2", "Moana 2",
			"How to Train Your Dragon (Live-Action)", "Minecraft", "Blade", "Thunderbolts", "Elio", "Twisters",
			"A Quiet Place: Day One" };

	@Test
	public void deleteAll() {
		boolean success = true;
		Video video = null;
		for (String id : idList) {
			try {
				video = videoService.findById(id);
				videoService.delete(video);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				success = false;
			}
		}
		assertTrue(success);
	}

	public void delete() {
		boolean success = true;
		Video video = videoService.findById("Avatar 3");
		try {
			videoService.delete(video);
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
		}
		assertTrue(success);
	}
}
