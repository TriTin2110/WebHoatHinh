package vn.tritin.WebHoatHinh.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vn.tritin.WebHoatHinh.dao.DAONews;
import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	private DAONews dao;

	@Autowired
	public NewsServiceImpl(DAONews dao) {
		this.dao = dao;
	}

	@Override
	public News save(News news) {
		// TODO Auto-generated method stub
		return dao.save(news);
	}

	@CachePut("news")
	private List<News> updateListNews() {
		return dao.findAll();
	}

	@Override
	public News findById(String id) {
		// TODO Auto-generated method stub
		Optional<News> opt = dao.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Cacheable("news")
	public List<News> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
