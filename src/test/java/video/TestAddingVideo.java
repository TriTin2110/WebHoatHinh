package video;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.service.VectorStoreService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.video.AttributeAddition;

@SpringBootTest(classes = vn.tritin.WebHoatHinh.WebHoatHinhApplication.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
public class TestAddingVideo {
	@Autowired
	private VideoService videoService;
	@Autowired
	private FileService fileService;
	@Autowired
	private AttributeAddition addition;
	@Value("${path.video}")
	private String pathVideo;
	@Value("${path.avatar}")
	private String pathImage;

	@Autowired
	private VectorStoreService vectorService;

	@Test
	public void addingOneVideo() {
		String pathAvatar = null;
		String pathVideoo = null;
		boolean result = true;
		try {
			File videoFile = new File("D:\\Code\\Project\\WebHoatHinh\\Demo\\Clips\\Test.mp4");
			File image = new File("D:\\Code\\Project\\WebHoatHinh\\Demo\\Images\\Test.jpg");
			MultipartFile fileImage = new MockMultipartFile("file", image.getName(), "img/jpg",
					new FileInputStream(image));
			MultipartFile fileVideo = new MockMultipartFile("file", videoFile.getName(), "video/mp4",
					new FileInputStream(videoFile));
			pathAvatar = fileService.saveFile(pathImage, fileImage);
			pathVideoo = fileService.saveFile(pathVideoo, fileVideo);
			System.out.println("da luu file");
			VideoCreator creator = new VideoCreator();
			creator.setId("Video Demo 4");
			creator.setCategories("Hài hước,Kinh dị");
			creator.setCountry("Anh");
			creator.setDescription(
					"Video Demo là một bộ phim đến từ Anh Quốc mang chủ đề kinh dị, hài hước do đạo diễn NTT chỉ đạo");
			creator.setDirector("NTT");
			creator.setLanguage("Tiếng Anh");
			creator.setPathAvatar(pathAvatar);
			creator.setPathVideo(pathVideoo);
			creator.setStudio("NTT Studio");

			System.out.println("da tao creator");
			Video video = addition.createAttribute(creator);
			System.out.println("da tao doi tuong");
			videoService.saveAndFlush(video);
			VectorStoreDTO vectorDTO = new VectorStoreDTO(creator.getId(), creator.getCategories(),
					creator.getDirector(), creator.getLanguage(), creator.getDescription(), 0);
			vectorService.insertData(vectorDTO);
			System.out.println("Da insert");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		assertTrue(result);
	}
}
