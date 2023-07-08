package com.rp.cafe.application.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersCrudRepository extends CrudRepository<Users, Long> {

	@Query("SELECT u FROM Users u WHERE u.username = :username")
	public Users getUserByUsername(@Param("username") String username);

}
