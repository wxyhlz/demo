package com.wxyh.demo.web.job;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobExample4SpringTask {
	
	private final Logger logger = LoggerFactory.getLogger("com.wxyh.demo.web.job");
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	@Scheduled(cron = "0 * * * * ?")
	public void execute() {
		int execBath = ai.incrementAndGet();
		logger.debug("执行JobExample4SpringTask, 批次={}", execBath);
		logger.info("执行JobExample4SpringTask, 批次={}", execBath);
		logger.warn("执行JobExample4SpringTask, 批次={}", execBath);
		logger.error("执行JobExample4SpringTask, 批次={}", execBath);
	}
	
}
