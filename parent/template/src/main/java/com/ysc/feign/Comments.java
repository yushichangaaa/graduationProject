package com.ysc.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "comment")
public interface Comments {

	@PostMapping(value = "/comment2")
	public Map<String,Object> getComments();
}
