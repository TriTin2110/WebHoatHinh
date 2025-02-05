package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Studio;

public interface StudioService {
	public Studio findByName(String name);

	public void add(Studio studio);

	public void merge(Studio studio);
}
