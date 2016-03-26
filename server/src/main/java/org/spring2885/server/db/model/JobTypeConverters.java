package org.spring2885.server.db.model;

import org.spring2885.model.JobType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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

	public class JsonToDbConverter implements Function<JobType, DbJobType> {
		 
		private Supplier<DbJobType> dbSupplier = Suppliers.ofInstance(new DbJobType());
		 
		public JsonToDbConverter() {
		}
		 
		public void withDbPersonType(DbJobType db){
			this.dbSupplier = Suppliers.ofInstance(db);
		}
		 
		@Override
		public DbJobType apply(JobType p) {
			DbJobType pT = dbSupplier.get();
			pT.setId(p.getId());
			pT.setName(p.getName());
			return pT;
		}
		 
	}
	 
	 private JobTypeConverters() {}
}