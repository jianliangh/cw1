package com.rp.cafe.application.model.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{
	
	@Query(value="SELECT * FROM transaction WHERE user_id = :user_id ;",nativeQuery=true)
	List<Transaction> sortASCbyUserID(@Param("user_id") long user_id);
	
	@Query(value="SELECT * FROM transaction WHERE user_id = :user_id ORDER BY tranid DESC;",nativeQuery=true)
	List<Transaction> sortDESCbyUserID(@Param("user_id") long user_id);
	
}
