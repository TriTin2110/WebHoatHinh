package vn.tritin.WebHoatHinh.util.studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.entity.Studio;
import vn.tritin.WebHoatHinh.service.StudioService;

@Component
public class StudioInteraction {
	private StudioService studioSer;

	@Autowired
	public StudioInteraction(StudioService studioSer) {
		this.studioSer = studioSer;
	}

	public Studio findStudio(String studioName) {
		Studio studio = studioSer.findByName(studioName);
		if (studio == null) {
			studio = new Studio(studioName);
			studioSer.add(studio);
		}
		return studio;
	}

	public Studio add(Studio studio) {
		return studioSer.add(studio);
	}

	public void update(Studio studio) {
		studioSer.merge(studio);
	}

	public Studio setCountryAndVideoForStudio(String studioName, Country country) {
		Studio studio = findStudio(studioName);
		studio.setCountry(country);
		return studio;
	}
}
