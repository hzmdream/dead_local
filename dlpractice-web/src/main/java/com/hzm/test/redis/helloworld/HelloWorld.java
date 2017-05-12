package com.hzm.test.redis.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.JedisCluster;
import dl.dlutils.utils.redis.JedisClusterFactory;



/**
 * @author houzm
 * @time 2017年3月23日下午6:40:17
 * redis - hello, world!
 */
public class HelloWorld {

	 /** 
     * 在不同的线程中使用相同的Jedis实例会发生奇怪的错误。但是创建太多的实现也不好因为这意味着会建立很多sokcet连接， 
     * 也会导致奇怪的错误发生。单一Jedis实例不是线程安全的。为了避免这些问题，可以使用JedisPool, 
     * JedisPool是一个线程安全的网络连接池。可以用JedisPool创建一些可靠Jedis实例，可以从池中拿到Jedis的实例。 
     * 这种方式可以解决那些问题并且会实现高效的性能 
     */  
	
	public static void main(String[] args) {
		/*ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
        RedisClientTemplate jedisCluster = (RedisClientTemplate)ac.getBean("jedisCluster");
        jedisCluster.set("a", "abc");
        System.out.println(jedisCluster.get("a"));*/
		ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:spring_config/applicationContext-redis.xml");
		JedisCluster jedisCluster = (JedisCluster)ac.getBean("jedisCluster");
		jedisCluster.set("a", "abc");
        System.out.println(jedisCluster.get("a"));
		/*try {
			transactionalForRedis();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Transactional
	public static void transactionalForRedis() throws Exception {
		ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:spring_config/applicationContext-redis.xml");
		RedisTemplate<String, String> jedisCluster = (RedisTemplate<String, String>)ac.getBean("redisTemplateTransactional");
		jedisCluster.boundValueOps("name").set("TEST_XXX");
		jedisCluster.boundValueOps("age").set("11");
		for (int i = 0; i < 5; i++) {
			if (i==3) {
				throw new RuntimeException("asdf");
			}
		}
	}
}
