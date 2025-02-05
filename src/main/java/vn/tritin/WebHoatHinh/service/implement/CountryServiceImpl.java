package vn.tritin.WebHoatHinh.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAOCountry;
import vn.tritin.WebHoatHinh.entity.Country;
import vn.tritin.WebHoatHinh.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	private DAOCountry dao;

	@Autowired
	public CountryServiceImpl(DAOCountry dao) {
		this.dao = dao;
	}

	@Override
	public Country findByName(String name) {
		// TODO Auto-generated method stub
		return dao.findByName(name);
	}

	@Override
	public void save(Country country) {
		// TODO Auto-generated method stub
		dao.save(country);
	}

	@Override
	public void merge(Country country) {
		// TODO Auto-generated method stub
		dao.saveAndFlush(country);
	}

}
