package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Country;

public interface CountryService {
	public Country findByName(String name);

	public Country save(Country country);

	public Country merge(Country country);
}
