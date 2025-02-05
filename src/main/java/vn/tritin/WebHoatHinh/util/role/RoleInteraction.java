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

	public Role find(String id) {
		Role role = roleSer.selectById(id);
		if (role == null) {
			role = new Role(id);
			roleSer.save(role);
			role = roleSer.selectById(id);
		}
		return role;
	}

	public void update(Role role) {
		roleSer.merge(role);
	}
}
