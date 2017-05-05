package dl.dlutils.utils.redis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>,InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(JedisClusterFactory.class);
	
	private Resource addressConfig;
	private String addressKeyPrefix;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;
	private Pattern pattern;

	public JedisClusterFactory() {
		this.pattern = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	}

	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = parseHostAndPort();
		this.jedisCluster = new JedisCluster(haps, this.timeout.intValue(),
				this.maxRedirections.intValue(), this.genericObjectPoolConfig);
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Iterator<?> localIterator = prop.keySet().iterator(); localIterator.hasNext();) {
				Object key = localIterator.next();
				if (!(((String) key).startsWith(this.addressKeyPrefix))) {
					continue;
				}
				String value = (String) prop.get(key);

				boolean isIpPort = this.pattern.matcher(value).matches();
				if (!(isIpPort)) {
					throw new IllegalArgumentException("IP OR PORT NOT ILLEGAL");
				}
				String[] ipAndPort = value.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0],
						Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception e) {
			throw new Exception("resolve jedis config failure", e);
		}
	}

	public JedisCluster getObject() throws Exception {
		return this.jedisCluster;
	}

	public Class<? extends JedisCluster> getObjectType() {
		return ((this.jedisCluster != null) ? this.jedisCluster.getClass()
				: JedisCluster.class);
	}

	public boolean isSingleton() {
		return true;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxRedirections() {
		return this.maxRedirections;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
		return this.genericObjectPoolConfig;
	}

	public void setGenericObjectPoolConfig(
			GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public Resource getAddressConfig() {
		return this.addressConfig;
	}

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public String getAddressKeyPrefix() {
		return this.addressKeyPrefix;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}
}