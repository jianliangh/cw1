package com.rp.cafe.application.model.card;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer>{
	
	@Query(value="SELECT COUNT(*) FROM card WHERE cardid = (:cardid) ;",nativeQuery=true)
	long checkCardExist(@Param("cardid") long cardid);

}
