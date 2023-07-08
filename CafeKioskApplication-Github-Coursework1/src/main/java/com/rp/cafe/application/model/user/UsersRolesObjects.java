package com.rp.cafe.application.model.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.rp.cafe.application.model.user.UsersRolesObjects;
import com.rp.cafe.application.model.user.role.RoleRepository;
import com.rp.cafe.application.model.user.role.UserRole;

public class UsersRolesObjects {
	
	@Autowired
	private UsersJpaRepository repo;
	
	@Autowired
	private RoleRepository rolerepo;
	
	Optional<Users> users;
	List<UserRole> roles;
	
	public Optional<Users> getUsers() {
		return users;
	}
	
	public void setUsers(Optional<Users> optional) {
		this.users = optional;
	}
	
	public List<UserRole> getRoles() {
		return roles;
	}
	
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
}
