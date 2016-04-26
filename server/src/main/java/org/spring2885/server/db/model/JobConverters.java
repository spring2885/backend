package org.spring2885.server.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.model.Job;
import org.spring2885.model.Person;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Strings;

@Component
public final class JobConverters {
    private static final Logger logger = LoggerFactory.getLogger(JobConverters.class);
    @Autowired
    private PersonService personService;
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
            j.setJobType(db.getJobType());
            j.setHours(db.getHours());
            j.setStartDate(ConverterUtils.asModelDate(db.getstartDate()));
            j.setEndDate(ConverterUtils.asModelDate(db.getendDate()));
            DbPerson dbPerson = db.getPerson();
            if (dbPerson != null) {
                j.setPostedBy(personFromDbToJson.apply(dbPerson));
            } else {
                logger.info("job {} had a null posted_by", db.getId());
            }
            
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
			
			db.setTitle(p.getTitle());
			db.setCompany(p.getCompany());
			db.setLocation(p.getLocation());
			db.setJobType(p.getJobType());
		
			Person postedBy = p.getPostedBy();
			if (postedBy != null 
			    && !Strings.isNullOrEmpty(postedBy.getEmail()) 
			    && db.getPerson() == null) {
			    DbPerson dbPostedBy = personService.findByEmail(postedBy.getEmail());
			    db.setPostedBy(dbPostedBy);    
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
