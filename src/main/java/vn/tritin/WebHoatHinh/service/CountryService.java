package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Country;

public interface CountryService {
	public Country findByName(String name);

	public void save(Country country);

	public void merge(Country country);
}
