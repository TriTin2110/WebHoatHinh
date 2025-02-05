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

	public Video createOtherEntity(VideoCreator videoCreator, Video video) {
		String countryName = videoCreator.getCountry();
		String categoriesName = videoCreator.getCategories();
		String studioName = videoCreator.getStudio();

		if (countryName != null) {
			Country country = countryInt.findCountry(countryName);
			country.getVideos().add(video);
			countryInt.update(country);

			video.setCountry(country);
		}
		if (categoriesName != null) {
			String[] categoryListStr = categoriesName.split(",");
			List<Category> categories = new ArrayList<Category>();

			for (String name : categoryListStr) {
				Category category = categoryInt.findCategories(name);
				category.getVideos().add(video);
				categoryInt.update(category);

				categories.add(category);
			}

			video.setCategories(categories);
		}
		if (studioName != null) {
			Studio studio = studioInt.findStudio(studioName);
			studio.getVideos().add(video);
			studioInt.update(studio);

			video.setStudio(studio);
		}

		return video;
	}

	public Video createAttribute(VideoCreator videoCreator) {
		Video video = new Video();
		video.setId(videoCreator.getId());
		video.setName(videoCreator.getName());
		video.setAvatar(videoCreator.getAvatar());
		video.setDirector(videoCreator.getDirector());
		video.setLanguage(videoCreator.getLanguage());
		return video;
	}

	public VideoDetail createVideoDetail(Video video, String storingPath) {
		VideoDetail detail = new VideoDetail(video.getId(), storingPath, video);
		return detail;
	}

	public VideoTag createVideoTag(Video video) {
		VideoTag tag = new VideoTag(video.getId(), video.getName(), video.getAvatar(), "Phim lẻ", null, 0, video);
		return tag;
	}
}
