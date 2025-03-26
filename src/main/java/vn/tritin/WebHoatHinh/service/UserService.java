package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.User;

public interface UserService {
	public User add(User user) throws Exception;

	public boolean update(User user);

	public void delete(User user);
}
