package com.wxyh.demo.web.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobExample2 implements Job {

	private final Logger logger = LoggerFactory.getLogger(QuartzJobExample2.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info(context.getJobDetail().getDescription());
	}

}
