package org.spring2885.server.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.spring2885.model.Job;
import org.spring2885.server.db.service.JobTypeService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
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
    public FromDbToJson fromDbToJson() {
        return new FromDbToJson();
    }

    public static class FromDbToJson implements Function<DbJob, Job> {

        @Override
        public Job apply(DbJob db) {
            Job j = new Job();
            j.setId(db.getId());
            j.setTitle(db.getTitle());
            j.setLocation(db.getLocation());
            j.setDescription(db.getDescription());
            j.setCompany(db.getCompany());
            
 
            

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

            Map<String, DbJobType> jobTypes = jobTypeService.findAll().stream()
                    .collect(Collectors.toMap(DbJobType::getName, (s) -> s));

            DbJob db = dbSupplier.get();

            db.setTitle(p.getTitle());
            db.setIndustry(db.getIndustry());
            db.setLocation(db.getLocation());
            db.setDescription(db.getDescription());
            db.setCompany(db.getCompany());
            

            return db;
        }

		public DbJob apply(DbJob dbJob, Job jobs) {
			// TODO Auto-generated method stub
			return null;
		}

    }
}