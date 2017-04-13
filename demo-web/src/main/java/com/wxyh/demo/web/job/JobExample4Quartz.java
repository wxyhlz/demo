package com.wxyh.demo.web.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class JobExample4Quartz {
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void execute() {
		String now = sdf.format(new Date());
		System.out.println(now + ", JobExample4Quartz, 执行批次=" + ai.incrementAndGet());
	}
	
}
