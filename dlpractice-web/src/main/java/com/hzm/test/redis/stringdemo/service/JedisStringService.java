package com.hzm.test.redis.stringdemo.service;

import com.hzm.test.redis.stringdemo.vo.UserInfoVO;

/**
 * @author houzm
 * @time 2017年4月4日上午11:53:12
 *
 */
public interface JedisStringService {

	/**
	 * @author houzm
	 * @time 2017年4月4日下午12:00:55
	 * @description 获取键key对应的键值value
	 * @params @param key 键的名称
	 * @params @return 键对应的键值
	 * @return String
	 */
	String getUser(String key);
	/**
	 * @author houzm
	 * @time 2017年4月4日下午12:02:23
	 * @description 新增键key(模拟数据库自增)，该键对应的键值为value
	 * @params @param incrkey 自增生成id的键名称 user.idincr
	 * @params @param value 新增键值对的值
	 * @params @return 返回新增键的结果，成功：，失败：
	 * @return String
	 */
	String setUser(UserInfoVO userInfo);
	/**
	 * @author houzm
	 * @time 2017年4月4日下午12:09:38
	 * @description 获取所有的键值对
	 * @return 
	 * @return String
	 */
	String getAllUsers();
}
