package vn.tritin.WebHoatHinh.util.studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
			studio = studioSer.findByName(studioName);
		}
		return studio;
	}

	public void update(Studio studio) {
		studioSer.merge(studio);
	}
}
