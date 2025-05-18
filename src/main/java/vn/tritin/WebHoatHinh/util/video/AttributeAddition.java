package vn.tritin.WebHoatHinh.util.video;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Category;
import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.entity.Studio;
import vn.tritin.WebHoatHinh.entity.Video;
import vn.tritin.WebHoatHinh.entity.VideoDetail;
import vn.tritin.WebHoatHinh.entity.VideoTag;
import vn.tritin.WebHoatHinh.model.VideoCreator;
import vn.tritin.WebHoatHinh.util.category.CategoryInteraction;
import vn.tritin.WebHoatHinh.util.country.CountryInteraction;
import vn.tritin.WebHoatHinh.util.studio.StudioInteraction;

@Component
public class AttributeAddition {
	private CountryInteraction countryInt;
	private CategoryInteraction categoryInt;
	private StudioInteraction studioInt;

	@Autowired
	public AttributeAddition(CountryInteraction countryInt, CategoryInteraction categoryInt,
			StudioInteraction studioInt) {
		this.countryInt = countryInt;
		this.categoryInt = categoryInt;
		this.studioInt = studioInt;
	}

	public Video createAttribute(VideoCreator videoCreator) {
		Video video = new Video();

		String videoPath = videoCreator.getPathVideo();
		String avatarPath = videoCreator.getPathAvatar();

		video.setId(videoCreator.getId());
		video.setBanner(avatarPath);
		video.setDirector(videoCreator.getDirector());
		video.setLanguage(videoCreator.getLanguage());

		video = createOtherEntity(videoCreator, video);

		video.setVideoDetail(createVideoDetail(videoPath, video));

		video.setVideoTag(createVideoTag(video));

		return video;
	}

	public Video createOtherEntity(VideoCreator videoCreator, Video video) {
		String countryName = videoCreator.getCountry();
		String categoriesName = videoCreator.getCategories();
		String studioName = videoCreator.getStudio();
		Country country = null;
		List<Category> categories = new ArrayList<Category>();
		Studio studio = null;

		if (countryName != null) {
			country = countryInt.findCountry(countryName);
		}

		if (categoriesName != null) {
			String[] categoryListStr = categoriesName.split(",");

			for (String name : categoryListStr) {
				Category category = categoryInt.findCategories(name);
				categories.add(category);
			}
		}

		if (studioName != null && countryName != null) {
			studio = studioInt.setCountryAndVideoForStudio(studioName, country);
		}

		video.setCategories(categories);
		video.setStudio(studio);
		video.setCountry(country);

		return video;
	}

	public VideoDetail createVideoDetail(String videoPath, Video video) {
		VideoDetail detail = new VideoDetail(video.getId(), videoPath, video);
		return detail;
	}

	public VideoTag createVideoTag(Video video) {
		VideoTag tag = new VideoTag(video.getId(), video.getBanner(), "Phim lẻ", null, 0, video);
		return tag;
	}
}
