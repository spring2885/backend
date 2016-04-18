package org.spring2885.server.db.model;

import org.spring2885.model.Job;
import org.spring2885.model.Person;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Strings;

@Component
public final class JobConverters {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonTypeService personTypeService;
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
			    DbPerson dbPostedBy = personService.findByEmail(postedBy.getEmail());
			    db.setPostedBy(dbPostedBy);
			}
			// TODO(jen): Finish setting these.
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
