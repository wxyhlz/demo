package com.wxyh.demo.web.job;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JobExample4Quartz {
	
	private final Logger logger = LoggerFactory.getLogger(JobExample4Quartz.class);
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	public void execute() {
		int execBath = ai.incrementAndGet();
		logger.debug("执行JobExample4Quartz, 批次={}", execBath);
		logger.info("执行JobExample4Quartz, 批次={}", execBath);
		logger.warn("执行JobExample4Quartz, 批次={}", execBath);
		logger.error("执行JobExample4Quartz, 批次={}", execBath);
		
	}
	
}
