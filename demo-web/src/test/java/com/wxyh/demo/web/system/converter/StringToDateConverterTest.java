package com.wxyh.demo.web.system.converter;

import org.junit.Test;

public class StringToDateConverterTest {

	StringToDateConverter converter = new StringToDateConverter();
	
	@Test
	public void testConvert() {
		System.out.println(converter.convert("2017-04-18"));
		System.out.println(converter.convert("2017-4-8"));
		System.out.println(converter.convert("2017-4-8 23:51:8"));
		System.out.println(converter.convert("2017-4-8  23:51:8"));
	}
	
}
