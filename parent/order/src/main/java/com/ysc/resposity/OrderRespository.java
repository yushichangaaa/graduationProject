package com.ysc.resposity;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.ysc.entity.Order;

public interface OrderRespository extends MongoRepository<Order, String>{

	Order findByUid(String uid);
	
	void deleteByUid(String uid);
	
}
