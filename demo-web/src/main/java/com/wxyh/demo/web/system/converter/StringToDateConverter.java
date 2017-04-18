package com.wxyh.demo.web.system.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
/**
 * 字符串转日期转换器
 * @author liuzhe
 *
 */
public class StringToDateConverter implements Converter<String, Date> {

	private static final String PATTERN_DATE = "yyyy-MM-dd";
	
	private static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	private static final String REGEX_DATE = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
	
	private static final String REGEX_DATETIME = "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
	
	@Override
	public Date convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		if (Pattern.matches(REGEX_DATE, source)) {
			return parseDate(PATTERN_DATE, source);
		}
		if (Pattern.matches(REGEX_DATETIME, source)) {
			return parseDate(PATTERN_DATETIME, source);
		}
		throw new IllegalArgumentException("Invalid source. source=" + source);
	}

	private Date parseDate(String pattern, String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

}
