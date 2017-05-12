package dl.test.jedis;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.79.132",6379);
		jedis.set("mytest", "123");
		String value = jedis.get("mytest");
		System.out.println(value);
		jedis.close();
	}
}
