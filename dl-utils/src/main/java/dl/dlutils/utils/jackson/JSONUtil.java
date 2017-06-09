package dl.dlutils.utils.jackson;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * 
 * @author houzm
 * @time 2017年4月4日下午11:20:41
 * @descriptionJackson JSON以高速、方便和灵活著称。 但是美中不足的一点就是对于中文的处理。当然我说的美中不足是在默认情况下，
 *                     Jackson JSON不会将中文等非ASCII字符转换为\uFFFF这样的形式来显示。
 *                     也就是说默认情况下会显示为{"name":"张三"}而不是{"name":"\u5F20\u4E09"}。
 *                     那么为什么有这样的需求呢？在HTTP协议中，我们可以指定数据头部分的内容编码。
 *                     如：“GBK”、“UTF-8”等等。如果你设置正确了，那么OK，前者所表示的数据您可以正确处理。
 *                     然而如果设置错误，对于中文字符将会产生乱码。两套应用系统对接，有可能两边使用的默认编码不同，
 *                     如果一方修改默认编码将会对应用造成不可预知的后果。因此若能以长远的眼光开发，
 *                     那么无论您设置成什么编码方式，都不会使数据产生乱码。因为，这里用到了万国编码——Unicode。
 */
public class JSONUtil {

	private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

	public static ObjectMapper getObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		//使Jackson JSON支持Unicode编码非ASCII字符
		//没有实现编码为unicode，因为CustomSerializerFactory所在的包在jackson2.8.7版本中还未找到，下面的是org.codehaus.jackson.map.ser.CustomSerializerFactory旧版本包中的
//		CustomSerializerFactory serializerFactory= new CustomSerializerFactory();
//		serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());
//		om.setSerializerFactory(serializerFactory);
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		om.configure(SerializationFeature.INDENT_OUTPUT, true);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		om.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return om;
	}

	public static String toJsonString(Object object) {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void writeJsonString(Object object, Writer writer) {
		try {
			getObjectMapper().writeValue(writer, object);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Map<?, ?> parseJson(String jsonString) {
		JsonNode jn = null;
		try {
			jn = getObjectMapper().readTree(jsonString);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return (Map<?, ?>) JsonNodeToMap(jn);
	}

	public static Object parseJson2MapOrList(String jsonString) {
		JsonNode jn = null;
		try {
			jn = getObjectMapper().readTree(jsonString);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return JsonNodeToMap(jn);
	}

	public static <T> T parseJson(String jsonString, Class<T> classType) {
		try {
			return getObjectMapper().readValue(jsonString, classType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> T parseJson(String jsonString,
			TypeReference<T> typeReference) {
		try {
			return getObjectMapper().readValue(jsonString, typeReference);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> T parseJsonT(String jsonString) {
		try {
			return getObjectMapper().readValue(jsonString,
					new TypeReference<Object>() {
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> Map<?, ?> bean2Map(Object bean) {
		try {
			return (Map<?, ?>) getObjectMapper().convertValue(bean, Map.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> T map2Bean(Map<?, ?> map, Class<T> clazz) {
		try {
			return getObjectMapper().convertValue(map, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Object JsonNodeToMap(JsonNode root) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (root == null) {
			return map;
		}
		if (root.isArray()) {
			List<Object> list = new ArrayList<Object>();
			for (JsonNode node : root) {
				Object nmp = JsonNodeToMap(node);
				list.add(nmp);
			}
			return list;
		}
		if (root.isTextual()) {
			try {
				return ((TextNode) root).asText();
			} catch (Exception e) {
				return root.toString();
			}
		}
		Iterator<?> iter = root.fields();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			JsonNode ele = (JsonNode) entry.getValue();
			if (ele.isObject())
				map.put(key, JsonNodeToMap(ele));
			else if (ele.isTextual())
				map.put(key, ((TextNode) ele).asText());
			else if (ele.isArray())
				map.put(key, JsonNodeToMap(ele));
			else {
				map.put(key, ele.toString());
			}
		}
		return map;
	}
}
