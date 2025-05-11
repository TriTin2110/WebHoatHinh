package vn.tritin.WebHoatHinh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.tritin.WebHoatHinh.entity.Comment;
import vn.tritin.WebHoatHinh.entity.VideoDetail;

@RepositoryRestResource
public interface DAOComment extends JpaRepository<Comment, String> {
	public List<Comment> findByVideoDetail(VideoDetail videoDetail);
}
