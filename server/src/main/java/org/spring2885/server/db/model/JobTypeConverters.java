package org.spring2885.server.db.model;

import org.spring2885.model.JobType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public final class JobTypeConverters {
	@Bean
	public FromDbToJson jobTypeFromDbToJson() {
		return new FromDbToJson();
	}
	 
	public static class FromDbToJson implements Function<DbJobType, JobType> {
		@Override
		public JobType apply(DbJobType db) {
			JobType p = new JobType();
			p.setId(db.getId());
			p.setName(db.getName());
			return p;
		}	 
	}
	 
	@Bean
	public JsonToDbConverter jobTypeJsonToDb() {
		return new JsonToDbConverter();
	}

	public class JsonToDbConverter {
		 
		JsonToDbConverter() {
		}
		 
		public DbJobType apply(DbJobType db, JobType p) {
			db.setId(p.getId());
			db.setName(p.getName());
			return db;
		}
		 
	}
	 
	 private JobTypeConverters() {}
}