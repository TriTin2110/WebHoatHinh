package video;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
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
import vn.tritin.WebHoatHinh.entity.VideoAnalyst;
import vn.tritin.WebHoatHinh.model.VectorStoreDTO;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.service.VectorStoreService;
import vn.tritin.WebHoatHinh.service.VideoService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.video.AttributeAddition;

@SpringBootTest(classes = vn.tritin.WebHoatHinh.WebHoatHinhApplication.class) // Khởi tạo toàn bộ Spring
																				// ApplicationContext giống như khi ứng
																				// dụng thực sự chạy.
//@Transactional // Spring sẽ mở một transaction cho mỗi test case, và rollback lại sau khi test
// xong.
@AutoConfigureTestDatabase(replace = Replace.NONE) // Ngăn Spring Boot tự động thay thế database thật bằng database
													// memory
@TestInstance(Lifecycle.PER_CLASS) // JUnit chỉ tạo một instance duy nhất của lớp test → cho phép dùng @BeforeAll
									// dưới dạng non-static.
public class TestAdding {
	private final int LIMIT = 20;
	private final int BEGIN = 1;
	private List<VideoCreator> creators;
	private List<MultipartFile> videoFiles;
	private List<MultipartFile> bannerFiles;

	@Autowired
	private VideoService videoService;
	private List<Video> videos;
	@Autowired
	private AttributeAddition attribute;
	@Autowired
	private VectorStoreService vectorStoreService;
	@Autowired
	private FileService fileService;
	@Value("${path.video}")
	private String pathVideo;

	@Value("${path.avatar}")
	private String pathAvatar;

	private final String VIDEO_PATH_TEST = "D:\\Code\\Project\\WebHoatHinh\\Demo\\Clips";
	private final String BANNER_PATH_TEST = "D:\\Code\\Project\\WebHoatHinh\\Demo\\Images";

	@BeforeAll
	public void createData() {
		creators = new ArrayList<VideoCreator>();
		videoFiles = new ArrayList<MultipartFile>();
		bannerFiles = new ArrayList<MultipartFile>();
		videos = new ArrayList<Video>();

		String[] countryList = { "Việt Nam", "Hoa Kỳ", "Nhật Bản", "Hàn Quốc", "Trung Quốc", "Thái Lan", "Úc", "Canada",
				"Anh", "Pháp", "Đức", "Ý", "Tây Ban Nha", "Nga", "Brazil", "Argentina", "Ấn Độ", "Ai Cập", "Nam Phi",
				"Singapore" };
		String[] categoryList = { "Hành động", "Khoa học viễn tưởng", "Kinh dị", "Hài kịch", "Lãng mạn", "Hoạt hình",
				"Phiêu lưu", "Giật gân", "Tâm lý tội phạm", "Kỳ ảo" };
		String[] studioList = { "Warner Bros. Pictures", "Walt Disney Pictures", "Universal Pictures", "Marvel Studios",
				"Sony Pictures", "Paramount Pictures", "20th Century Studios", "Pixar Animation Studios", "Netflix",
				"A24" };
		String[] idList = { "Avatar 3", "Superman", "Captain America: Brave New World", "The Fantastic Four",
				"Mission: Impossible 8", "The Batman - Part II", "Gladiator 2", "Wicked: Part One",
				"Joker: Folie à Deux", "Mufasa: The Lion King", "Spider-Man: Beyond the Spider-Verse", "Inside Out 2",
				"Moana 2", "How to Train Your Dragon (Live-Action)", "Minecraft", "Blade", "Thunderbolts", "Elio",
				"Twisters", "A Quiet Place: Day One" };
		String[] languageList = { "Tiếng Việt", "Tiếng Anh", "Tiếng Nhật", "Tiếng Hàn", "Tiếng Trung", "Tiếng Thái",
				"Tiếng Anh", "Tiếng Anh, Tiếng Pháp", "Tiếng Anh", "Tiếng Pháp", "Tiếng Đức", "Tiếng Ý",
				"Tiếng Tây Ban Nha", "Tiếng Nga", "Tiếng Bồ Đào Nha", "Tiếng Tây Ban Nha", "Tiếng Hindi, Tiếng Anh",
				"Tiếng Ả Rập", "Tiếng Anh, Tiếng Zulu", "Tiếng Anh, Tiếng Trung" };
		String[] director = { "Steven Spielberg", "Christopher Nolan", "Martin Scorsese", "Quentin Tarantino",
				"James Cameron", "Ridley Scott", "Francis Ford Coppola", "Alfred Hitchcock", "Stanley Kubrick",
				"Peter Jackson", "David Fincher", "George Lucas", "Clint Eastwood", "Guillermo del Toro",
				"Wes Anderson", "Sofia Coppola", "Ang Lee", "Denis Villeneuve", "Hayao Miyazaki", "Spike Lee" };
		Random random = new Random();
		for (int i = 0; i < LIMIT; i++) {
			VideoCreator creator = new VideoCreator();
			creator.setId(idList[i]);
			creator.setLanguage(languageList[random.nextInt(20)]);
			creator.setCountry(countryList[random.nextInt(20)]);
			creator.setStudio(studioList[random.nextInt(10)]);
			creator.setCategories(categoryList[random.nextInt(10)] + "," + categoryList[random.nextInt(10)]);
			creator.setDirector(director[random.nextInt(20)]);
			creator.setDescription("This is demo");
			creators.add(creator);
		}
		try {
			for (int i = BEGIN; i <= LIMIT; i++) {
				File fileVideo = new File(VIDEO_PATH_TEST + File.separator + "clip" + i + ".mp4");
				FileInputStream inputVideo = new FileInputStream(fileVideo);
				MultipartFile multipartFileVideo = new MockMultipartFile("file", fileVideo.getName(), "video/mp4",
						inputVideo);
				videoFiles.add(multipartFileVideo);

				File fileImage = new File(BANNER_PATH_TEST + File.separator + "img_" + i + ".jpg");
				FileInputStream inputImage = new FileInputStream(fileImage);
				MultipartFile multipartFileImage = new MockMultipartFile("file", fileImage.getName(), "image/jpg",
						inputImage);
				bannerFiles.add(multipartFileImage);
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		String storingVideoPath = null;
		String storingAvatarPath = null;
		Video video = null;
		VideoCreator creator = null;
		try {
			for (int i = 0; i < LIMIT; i++) {
				creator = creators.get(i);
				storingVideoPath = fileService.saveFile(pathVideo, videoFiles.get(i));
				storingAvatarPath = fileService.saveFile(pathAvatar, bannerFiles.get(i));

				creator.setPathVideo(storingVideoPath);
				creator.setPathAvatar(storingAvatarPath);
				video = attribute.createAttribute(creator);
				videos.add(video);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Test
	public void addingMultiVideo() {
		boolean result = true;
		try {
			VectorStoreDTO vectorStoreDTO = null;
			for (Video video : videos) {
				video.setVideoAnalyst(new VideoAnalyst(video.getId(), video));
				videoService.saveAndFlush(video);
				vectorStoreDTO = new VectorStoreDTO(video.getId(),
						video.getCategories().stream().map(o -> o.getName()).collect(Collectors.joining(",")),
						video.getDirector(), video.getLanguage(), video.getDescription(), video.getViewer());
				vectorStoreService.insertData(vectorStoreDTO);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
		}
		assertTrue(result);
	}
}
