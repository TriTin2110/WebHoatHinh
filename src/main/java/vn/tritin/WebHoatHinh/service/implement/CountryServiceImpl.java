package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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
		Optional<Country> country = dao.findById(name);
		return country == null ? null : country.get();
	}

	@Override
	@Transactional
	public Country save(Country country) {
		// TODO Auto-generated method stub
		return dao.save(country);
	}

	@Override
	@Transactional
	public Country merge(Country country) {
		// TODO Auto-generated method stub
		return dao.saveAndFlush(country);
	}

	@Override
	@Cacheable("countries")
	public List<Country> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
