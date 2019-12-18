package com.jpa.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;

/**
 * 辅助工具类
 */
@SuppressWarnings("deprecation")
public class Helper {
	/**
	 * 判断一个对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean empty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String && ((String) o).length() == 0) {
			return true;
		}
		if (o instanceof List && ((List) o).size() == 0) {
			return true;
		}
		if (o instanceof Map && ((Map) o).size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 把一个字符串转化为int
	 * 
	 * @param num
	 * @return
	 */
	public static int parseToInt(String num) {
		num = num.trim();
		if (empty(num)) {
			return 0;
		}
		return Integer.parseInt(num);
	}

	/**
	 * 把一个字符串转化为long
	 * 
	 * @param num
	 * @return
	 */
	public static long parseToLong(String num) {
		num = num.trim();
		if (empty(num)) {
			return 0;
		}
		return Long.parseLong(num);
	}

	/**
	 * 把字符串转化为Double
	 * 
	 * @param num
	 * @return
	 */
	public static double parseToDobule(String num) {
		num = num.trim();
		if (empty(num)) {
			return 0;
		}
		return Double.parseDouble(num);
	}

	/**
	 * 把一个字符串转换为Float
	 * 
	 * @param num
	 * @return
	 */
	public static float parseToFloat(String num) {
		num = num.trim();
		if (empty(num)) {
			return 0;
		}
		return Float.parseFloat(num);
	}

	/**
	 * 把字符串转化为short
	 * 
	 * @param num
	 * @return
	 */
	public static short parseToShort(String num) {
		num = num.trim();
		if (empty(num)) {
			return 0;
		}
		return Short.parseShort(num);
	}

	/**
	 * 通过文件名获取一个文件的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex == -1) {
			return "";
		}
		String suffix = fileName.substring(lastDotIndex + 1);
		return suffix;
	}

	/**
	 * 获取一个UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 把一个JavaBean组件的属性设置为Null
	 * 
	 * @param page
	 * @param prop
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static void initPropNull(Object bean, String prop)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (bean instanceof PageBo) {
			for (Object item : ((PageBo) bean).getDataList()) {
				initPropNull(item, prop);
			}
		} else if (bean instanceof List) {
			for (Object item : ((List) bean)) {
				initPropNull(item, prop);
			}
		} else {
			String[] props = prop.split("\\.");
			if (props.length > 1) {
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				Object tempProp = null;
				for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
					if (pd.getName().equals(props[0])) {
						tempProp = pd.getReadMethod().invoke(bean);
						break;
					}
				}
				if (Helper.empty(tempProp)) {
					return;
				}
				String otherProp = "";
				for (int i = 1; i < props.length; i++) {
					otherProp += props[i] + ".";
				}
				otherProp = otherProp.substring(0, otherProp.length() - 1);
				initPropNull(tempProp, otherProp);
			} else {
				BeanUtils.setProperty(bean, prop, null);
			}
		}
	}

	/**
	 * 为Hibernate Query绑定参数
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Query bindParams(Query query, Map<String, Object> params) {
		Set<String> keys = params.keySet();
		for (String key : keys) {
			Object value = params.get(key);
			if (value instanceof List) {
				query.setParameterList(key, (List) value);
			} else {
				query.setParameter(key, value);
			}
		}
		return query;
	}

	/**
	 * 获取当前的日期转换成字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String currentDateStr(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}
}
