package com.wxyh.demo.web.job;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobExample4SpringTask {
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void execute() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName());
		System.out.println(new Date().getTime() + ", JobExample4SpringTask, 执行批次=" + ai.incrementAndGet());
	}
	
}
