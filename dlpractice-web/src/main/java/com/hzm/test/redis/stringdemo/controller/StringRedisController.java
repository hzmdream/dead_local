package com.hzm.test.redis.stringdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hzm.test.redis.stringdemo.service.JedisStringService;
import com.hzm.test.redis.stringdemo.vo.UserInfoVO;

@Controller
@RequestMapping("/redis/string")
@Scope("")
public class StringRedisController {
	
	@Autowired
	private JedisStringService jedisStringService;
	
	@RequestMapping(value="/setstring",method=RequestMethod.POST)
//	public String setString(@RequestParam("key") String key,@RequestParam("value") String value){
	public String setString(@RequestBody(required=true) UserInfoVO userInfo){
		String result = jedisStringService.setUser(userInfo);
		return result;
		
	}
	@RequestMapping(value="/getstring",method=RequestMethod.GET)
	public String getString(@RequestParam(value="key",required=true) String key){
		String value = jedisStringService.getUser(key);
		return value;
	}
	@RequestMapping(value="/getallkeys",method=RequestMethod.GET)
	public String getAllKeys(){
		String value = jedisStringService.getAllUsers();
		return value;
	}
	
}
