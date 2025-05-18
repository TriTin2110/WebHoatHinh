package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Tag;

public interface TagService {
	public Tag save(Tag tag);

	public Tag findById(String id);
}
