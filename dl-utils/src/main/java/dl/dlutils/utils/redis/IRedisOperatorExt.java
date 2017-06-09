package dl.dlutils.utils.redis;

import java.util.TreeSet;

public abstract interface IRedisOperatorExt {
	public abstract TreeSet<String> keys(String paramString);
}