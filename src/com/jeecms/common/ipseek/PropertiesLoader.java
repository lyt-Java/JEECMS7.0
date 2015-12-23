package com.jeecms.common.ipseek;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 装载淘宝、百度api地址属性
 */
public class PropertiesLoader {
	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesLoader.class);
	private static final String API_FILENAME = "api.properties";
	private static Properties properties = new Properties();

	static {

		// 1. load library defaults
		InputStream in = PropertiesLoader.class
				.getResourceAsStream(API_FILENAME);

		if (in == null) {
			logger.error("{} not found", API_FILENAME);
			throw new RuntimeException(API_FILENAME + " not found");
		} else {
			if (!(in instanceof BufferedInputStream))
				in = new BufferedInputStream(in);

			try {
				properties.load(in);
				in.close();
				logger.debug("{} loaded", API_FILENAME);
			} catch (Exception e) {
				logger.error("Error while processing {}", API_FILENAME);
				throw new RuntimeException("Error while processing "
						+ API_FILENAME, e);
			}
		}
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * 
	 * @see Properties#getProperty(String)
	 */
	public static String getProperty(final String key) {
		return properties.getProperty(key);
	}

	/**
	 * Sets the property with the specified key in this property list.
	 * 
	 * @see Properties#setProperty(String, String)
	 */
	public static void setProperty(final String key, final String value) {
		properties.setProperty(key, value);
	}

	
	/**
	 * Returns taobao api url
	 */
	public static String getTaobaoApi() {
		return properties.getProperty("api.taobao");
	}

	/**
	 * Returns baidu api  url
	 */
	public static String getBaiduApi() {
		return properties.getProperty("api.baidu");
	}

	/**
	 * Returns baidu api ak 
	 */
	public static String getBaiduApiAk() {
		return properties.getProperty("api.baidu.ak");
	}
}
