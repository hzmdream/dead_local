package com.hzm.test.jedis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShairdJedisPoolTest {

	public static void main(String[] args) {
		// 构建连接池配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 设置最大连接数
		poolConfig.setMaxTotal(50);

		// 定义集群信息
        List<JedisShardInfo>shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.79.132", 7001));
		shards.add(new JedisShardInfo("192.168.79.132", 7002));
		shards.add(new JedisShardInfo("192.168.79.132", 7003));
		shards.add(new JedisShardInfo("192.168.79.132", 7004));
		shards.add(new JedisShardInfo("192.168.79.132", 7005));
		shards.add(new JedisShardInfo("192.168.79.132", 7006));

		// 定义集群连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
        ShardedJedis shardedJedis = null;
		try {
			// 从连接池中获取到jedis分片对象
			shardedJedis = shardedJedisPool.getResource();
		
			for (int i = 0; i < 10; i++) {
                shardedJedis.set("key_" + i, "value_" + i);
            }

//			System.out.println(shardedJedis.get("key_49"));
//            System.out.println(shardedJedis.get("key_7"));

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (null != shardedJedis) {
				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
				shardedJedis.close();
            }
        }

		// 关闭连接池
		shardedJedisPool.close();

    }

}
