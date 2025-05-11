package vn.tritin.WebHoatHinh.service;

import java.util.List;

import vn.tritin.WebHoatHinh.entity.Comment;
import vn.tritin.WebHoatHinh.entity.VideoDetail;

public interface CommentService {
	public Comment save(Comment comment);

	public List<Comment> selectByVideoDetail(VideoDetail videoDetail);
}
