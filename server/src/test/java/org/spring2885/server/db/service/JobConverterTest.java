package org.spring2885.server.db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.spring2885.model.Job;
import org.spring2885.server.db.model.DbJob;
import org.spring2885.server.db.model.JobConverters;

import com.google.common.base.Function;

@RunWith(JUnit4.class)
public class JobConverterTest {
	public JobConverterTest(){}
	
	@Test
	public void testFromDbToJson(){
		Function<DbJob, Job> dtoj = JobConverters.fromDbToJson();
		DbJob dbp = new DbJob();
		Job p = dtoj.apply(dbp);
	}
	
	@Test
	public void testFromJsonToDb(){
		Function<Job, DbJob> jtod = JobConverters.fromJsonToDb();
		Job p = new Job();
		DbJob dbp = jtod.apply(p);
	}
}
