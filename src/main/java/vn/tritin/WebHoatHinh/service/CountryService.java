package vn.tritin.WebHoatHinh.service;

import java.util.List;

import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.entity.Video;

public interface CountryService {
	public Country findByName(String name);

	public Country save(Country country);

	public Country merge(Country country);

	public List<Country> findAll();

	public Country addVideo(String id, Video video);

}
