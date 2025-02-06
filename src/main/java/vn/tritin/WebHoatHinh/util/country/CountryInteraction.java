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
			country = add(country);
		}
		return country;
	}

	public Country add(Country country) {
		return service.save(country);
	}

	public void update(Country country) {
		service.merge(country);
	}

}
