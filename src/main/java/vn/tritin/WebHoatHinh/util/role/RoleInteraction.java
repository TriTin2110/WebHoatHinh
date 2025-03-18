package vn.tritin.WebHoatHinh.util.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.tritin.WebHoatHinh.entity.Role;
import vn.tritin.WebHoatHinh.service.RoleService;

@Component
public class RoleInteraction {
	private RoleService roleSer;

	@Autowired
	public RoleInteraction(RoleService roleSer) {
		this.roleSer = roleSer;
	}

	public Role find(String name) {
		Role role = roleSer.selectById(name);
		if (role == null) {
			role = new Role(name);
			role = roleSer.save(role);
		}
		return role;
	}

	public void update(Role role) {
		roleSer.merge(role);
	}
}
