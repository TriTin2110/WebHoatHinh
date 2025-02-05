package vn.tritin.WebHoatHinh.util.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.service.CountryService;

@Component
public class CountryInteraction {
	private CountryService service;

	@Autowired
	public CountryInteraction(CountryService service) {
		this.service = service;
	}

	public Country findCountry(String countryName) {
		Country country = service.findByName(countryName);
		if (country == null) {
			country = new Country(countryName);
			add(country);
			country = service.findByName(countryName);
		}
		return country;
	}

	public void add(Country country) {
		service.save(country);
	}

	public void update(Country country) {
		service.merge(country);
	}
}
