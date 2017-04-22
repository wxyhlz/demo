package com.wxyh.demo.common.utils;

import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class PropertiesUtils {

	public static int getIntPropValue(Properties props, String key, int defaultValue) {
		return NumberUtils.toInt(props.getProperty(key), defaultValue);
	}
	
	public static boolean getBooleanPropValue(Properties props, String key) {
		return BooleanUtils.toBoolean(props.getProperty(key));
	}
	
}
