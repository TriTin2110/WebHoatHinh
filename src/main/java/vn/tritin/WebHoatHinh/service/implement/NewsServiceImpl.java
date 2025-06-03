package vn.tritin.WebHoatHinh.service.implement;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import vn.tritin.WebHoatHinh.dao.DAONews;
import vn.tritin.WebHoatHinh.entity.News;
import vn.tritin.WebHoatHinh.entity.Tag;
import vn.tritin.WebHoatHinh.model.NewsCreator;
import vn.tritin.WebHoatHinh.service.NewsService;
import vn.tritin.WebHoatHinh.service.TagService;
import vn.tritin.WebHoatHinh.service.util.FileService;
import vn.tritin.WebHoatHinh.util.StringHandler;

@Service
public class NewsServiceImpl implements NewsService {
	@Value("${path.news-banner}")
	private String bannerPath;

	private DAONews dao;
	private TagService tagSer;
	private FileService fileSer;

	@Autowired
	public NewsServiceImpl(DAONews dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public News save(NewsCreator newsCreator, MultipartFile file) {
		// TODO Auto-generated method stub
		if (isNewsExists(newsCreator.getId()))
			return null;
		else {
			News news = prepareData(newsCreator, file);
			news = dao.save(news);
			return news;
		}
	}

	private boolean isNewsExists(String id) {
		return findById(id) != null;
	}

	@CachePut(value = "news")
	public List<News> updateListNews() {
		StringHandler stringHandler = new StringHandler();
		List<News> news = dao.findAll();
		news = news.stream().map(o -> {
			o.setDescription(stringHandler.decrypt(o.getDescription()));
			return o;
		}).toList();
		return news;
	}

	@Override
	public News findById(String id) {
		// TODO Auto-generated method stub
		Optional<News> opt = dao.findById(id);
		return (opt.isEmpty()) ? null : opt.get();
	}

	@Override
	@Cacheable(value = "news")
	public List<News> findAll() {
		// TODO Auto-generated method stub
		StringHandler stringHandler = new StringHandler();
		List<News> news = dao.findAll();
		news = news.stream().map(o -> {
			o.setDescription(stringHandler.decrypt(o.getDescription()));
			return o;
		}).toList();
		return news;
	}

	@Override
	@Transactional
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		if (id != null) {
			News news = findById(id);
			if (news == null)
				return false;
			else {
				dao.delete(news);
				return true;
			}
		}
		return false;
	}

	/*-
	 * To save News 
	 * First I will deflater description to minimum size (Notice that description will also contain tag html and img have been encrypted
	 * Then I will save the banner image 
	 * Also I will split all tags to an element of array (Split by ',') and put it to the tags list of news
	 * After that I will save news to db
	 *  
	 * */
	private News prepareData(NewsCreator newsCreator, @RequestParam("banner") MultipartFile file) {
		// Deflater Description
		Date date = new Date(System.currentTimeMillis());
		String id = newsCreator.getId();
		id = id.replace('/', '-');

		// Description Handling
		String description = newsCreator.getDescription();
		StringHandler stringHandler = new StringHandler();
		String output = stringHandler.encrypt(description);

		// Banner handling
		String banner = null;
		try {
			banner = fileSer.saveImage(bannerPath, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Tag handling
		String[] tagsSplitted = newsCreator.getTags().split(",");
		List<Tag> tags = new LinkedList<Tag>();
		Tag tempTag;
		News news = new News(id, output, date, newsCreator.getAuthorName(), banner);
		for (String tag : tagsSplitted) {
			if (tag.isBlank())
				continue;
			tempTag = tagSer.findById(tag);
			if (tempTag == null)
				tempTag = new Tag(tag, date, Arrays.asList(news));
			else
				tempTag.getNews().add(news);
			tags.add(tempTag);
		}
		news.setTags(tags);
		return news;
	}

	public void setTagSer(TagService tagSer) {
		this.tagSer = tagSer;
	}

	public void setFileSer(FileService fileSer) {
		this.fileSer = fileSer;
	}

}
