package org.spring2885.server.db.model;

import java.util.Map;
import java.util.stream.Collectors;

import org.spring2885.model.Job;
import org.spring2885.model.Person;
import org.spring2885.server.db.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

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
            j.setJobType(db.getjobType());
            j.setHours(db.getHours());
            j.setStartDate(db.getstartDate());
            j.setEndDate(db.getendDate());
            
            return j;
        }
    }

    @Bean
    public JsonToDbConverter jsonToDbConverter() {
        return new JsonToDbConverter();
    }

    public class JsonToDbConverter {

        public JsonToDbConverter() {
        }

		public DbJob apply(DbJob db, Job p) {
            Map<String, DbJobType> jobTypes = jobTypeService.findAll().stream()
                    .collect(Collectors.toMap(DbJobType::getName, (s) -> s));
            db.setId(p.getId());
            db.setTitle(p.getTitle());
            db.setLocation(p.getLocation());
            db.setDescription(p.getDescription());
            db.setCompany(p.getCompany());
            db.setjobType(p.getJobType());
            db.setHours(p.getHours());
            db.setstartDate(ConverterUtils.asSqlDate(p.getStartDate()));
            db.setendDate(ConverterUtils.asSqlDate(p.getEndDate()));
            
            
            return db;
		}

    }
}