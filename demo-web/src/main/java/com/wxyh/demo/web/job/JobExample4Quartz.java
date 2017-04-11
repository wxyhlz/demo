package com.wxyh.demo.web.job;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class JobExample4Quartz {
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	public void execute() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new Date().getTime() + ", JobExample4Quartz, 执行批次=" + ai.incrementAndGet());
	}
	
}
