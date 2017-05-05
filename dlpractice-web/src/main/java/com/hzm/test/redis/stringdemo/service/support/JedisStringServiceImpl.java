package com.hzm.test.redis.stringdemo.service.support;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import com.hzm.test.redis.stringdemo.service.JedisStringService;
import com.hzm.test.redis.stringdemo.vo.UserInfoVO;


@Service("jedisStringService")
public class JedisStringServiceImpl implements JedisStringService{
	
	@Autowired
//	private RedisClientTemplate redisClientTemplate;

	@Override
	public String getUser(String key) {
//		String value = redisClientTemplate.get(key);
//		return value;
		return null;
	}

	@Override
	public String setUser(UserInfoVO user) {
		/*//自增键自增 post.id.page.view
		Long incrValue = redisClientTemplate.incr(user.getIncrKey());
		//拼接键名称
		String key = "user."+incrValue+".info";
		//user object to json
		String userJsonString = JSONUtil.toJsonString(user);
		//设置键值
		String result = redisClientTemplate.set(key, userJsonString);*/
//		return result;
		return null;
	}

	@Override
	public String getAllUsers() {
//		ShardedJedis allShards = redisClientTemplate.get(key)
//		allShards.
		return null;
	}

	
}
