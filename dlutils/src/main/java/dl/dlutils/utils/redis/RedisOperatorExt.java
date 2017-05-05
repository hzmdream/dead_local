package dl.dlutils.utils.redis;


import java.util.Map;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/*@Repository*/
public class RedisOperatorExt implements IRedisOperatorExt {
	public static final Logger logger = LoggerFactory
			.getLogger(RedisOperatorExt.class);

	@Autowired
	private JedisCluster jedisCluster;

	public TreeSet<String> keys(String pattern) {
		logger.debug("Start getting keys...");
		TreeSet keys = new TreeSet();
		Map clusterNodes = this.jedisCluster.getClusterNodes();

		/*for (String node : clusterNodes.keySet()) {
			logger.debug("Getting keys from: {}", node);
			JedisPool jp = (JedisPool) clusterNodes.get(node);
			Jedis connection = jp.getResource();
			try {
				if (connection.isConnected())
					keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				logger.error("Getting keys error: {}", e);
			} finally {
				logger.debug("Connection closed.");
				connection.close();
			}
		}*/
		logger.debug("Keys gotten!");
		return keys;
	}
}