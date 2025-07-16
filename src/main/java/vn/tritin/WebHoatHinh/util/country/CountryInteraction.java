package vn.tritin.WebHoatHinh.util.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.entity.Video;
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
			service.save(country);
		}
		return country;
	}

	public Country addVideoToCountry(String id, Video video) {
		return this.service.addVideo(id, video);
	}

	public Country add(Country country) {
		return service.save(country);
	}

	public void update(Country country) {
		service.merge(country);
	}

}
