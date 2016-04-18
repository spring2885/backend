package org.spring2885.server.db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spring2885.model.Job;
import org.spring2885.model.Person;
import org.spring2885.server.db.service.JobService;
import org.spring2885.server.db.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Strings;

@Component
public final class JobConverters {
    @Autowired
    private JobService personService;
    @Autowired
    private JobTypeService personTypeService;
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;
   

    public class JobsFromDbToJson implements Function<DbJob, Job> {
		
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
	
    public class JsonToDbConverter {
		JsonToDbConverter() {
		}
		
		public DbJob apply(DbJob db, Job p) {
			db.setId(p.getId());
			if (!Strings.isNullOrEmpty(p.getTitle())) {
	            db.setTitle(p.getTitle());
			}
			if (!Strings.isNullOrEmpty(p.getDescription())) {
			    db.setDescription(p.getDescription());
			}
			Person postedBy = p.getPostedBy();
			if (postedBy != null 
			    && !Strings.isNullOrEmpty(postedBy.getEmail()) 
			    && db.getPerson() == null) {
			    // Only update the person if it hadn't been set already.
			    db.setpostedBy(personService.existsByTitle(postedBy.getTitle()));
			}
			return db;
		}
	}
		
	@Bean
	public JobsFromDbToJson jobsFromDbToJson() {
		return new JobsFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter jobsFromJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private JobConverters() {
	}
}
