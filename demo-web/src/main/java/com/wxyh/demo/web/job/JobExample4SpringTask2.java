package com.wxyh.demo.web.job;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class JobExample4SpringTask2 {
	
	private final AtomicInteger ai = new AtomicInteger(0);
	
	public void execute() {
		System.out.println(Thread.currentThread().getName());
		System.out.println(new Date().getTime() + ", JobExample4SpringTask2, 执行批次=" + ai.incrementAndGet());
	}
	
}
