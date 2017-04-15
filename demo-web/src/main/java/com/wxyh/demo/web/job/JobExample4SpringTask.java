package com.wxyh.demo.web.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobExample4SpringTask {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void execute() {
		String now = sdf.format(new Date());
		logger.error(now + ", JobExample4SpringTask, 执行批次=" + ai.incrementAndGet());
	}
	
}
