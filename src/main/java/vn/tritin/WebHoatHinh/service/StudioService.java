package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Studio;

public interface StudioService {
	public Studio findByName(String name);

	public Studio add(Studio studio);

	public Studio merge(Studio studio);
}
