package com.ysc.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ysc.entity.User2;

public interface User2Respository extends MongoRepository<User2,String>{

	User2 findByPhone(String phone);
	void deleteByPhone(String phone);
}
	