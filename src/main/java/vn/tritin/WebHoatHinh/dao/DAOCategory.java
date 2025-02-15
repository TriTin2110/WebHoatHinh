package vn.tritin.WebHoatHinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.tritin.WebHoatHinh.entity.Category;

@RepositoryRestResource
public interface DAOCategory extends JpaRepository<Category, String> {
}
