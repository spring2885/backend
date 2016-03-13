package org.spring2885.server.db.model;

import java.util.Map;
import java.util.stream.Collectors;

import org.spring2885.model.Job;
import org.spring2885.server.db.model.JobConverters.FromDbToJson;
import org.spring2885.server.db.model.JobConverters.JsonToDbConverter;
import org.spring2885.server.db.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

@Component
public final class JobConverters {
	
	@Autowired
    private JobTypeService jobTypeService;
    

    @Bean
    public FromDbToJson dbToJsonConverter() {
        return new FromDbToJson();
    }
	public class FromDbToJson implements Function<DbJob, Job> {

		@Override
		public Job apply(DbJob db) {
			Job j = new Job();
			j.setId(db.getId());
			j.setTitle(db.getTitle());
			//j.setIndustry(db.getIndustry());
			j.setLocation(db.getLocation());
			j.setDescription(db.getDescription());
			//j.setjobType(db.getjobType());
			//j.setstartDate(db.getstartDate());
			//j.setendDate(db.getendDate());
			//j.setpostedbypersonId(db.getpostedbyPersonId());
			//j.setHours(db.getHours());
			

			return j;
		}
	}
	
	@Bean
    public JsonToDbConverter jsonToDbConverter() {
        return new JsonToDbConverter();
    }
	
	public class JsonToDbConverter implements Function<Job, DbJob> {

		private Supplier<DbJob> dbSupplier = Suppliers.ofInstance(new DbJob());

		public JsonToDbConverter() {
		}

		public void withDbJob(DbJob db) {
			this.dbSupplier = Suppliers.ofInstance(db);
		}
		
		@Override
		public DbJob apply(Job p) {
	        
	        Map<String, DbJobType> jobTypes = 
	                jobTypeService.findAll().stream()
                    .collect(Collectors.toMap(DbJobType::getName, (s) -> s));
	        

            DbJob db = dbSupplier.get();
	
			// Leave the ID null since we're updating an existing person.
			db.setTitle(p.getTitle());
			//Integer jobType = p.getjobType();
			//if (jobType != null) {
			//	db.setjobType(p.getjobType());
			//} else {
			//	db.setjobType(0);
			//}
			db.setIndustry(db.getIndustry());
			db.setLocation(db.getLocation());
			db.setDescription(db.getDescription());
			db.setstartDate(db.getstartDate());
			db.setendDate(db.getendDate());
		
			return db;
	}
		

	
	}
}

	
