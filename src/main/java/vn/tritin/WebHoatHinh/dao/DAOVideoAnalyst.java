package vn.tritin.WebHoatHinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.tritin.WebHoatHinh.entity.VideoAnalyst;

@RepositoryRestResource
public interface DAOVideoAnalyst extends JpaRepository<VideoAnalyst, String> {

}
