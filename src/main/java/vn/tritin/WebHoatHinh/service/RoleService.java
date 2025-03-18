package vn.tritin.WebHoatHinh.service;

import vn.tritin.WebHoatHinh.entity.Role;

public interface RoleService {
	public Role selectById(String name);

	public Role save(Role role);

	public void merge(Role role);
}
