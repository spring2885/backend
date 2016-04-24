package org.spring2885.server.db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spring2885.model.News;
import org.spring2885.model.NewsComment;
import org.spring2885.model.Person;
import org.spring2885.server.db.model.NewsCommentConverters.NewsCommentFromDbToJson;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Strings;

@Component
public final class NewsConverters {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonTypeService personTypeService;
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;
    @Autowired
    private NewsCommentFromDbToJson newsCommentFromDbToJson;

    public class NewsFromDbToJson implements Function<DbNews, News> {
		
		@Override
		public News apply(DbNews db) {
			News n = new News();
			n.setId(db.getId());
			n.setDescription(db.getDescription());
			n.setExpired(db.getExpired());
			DbPerson person = db.getPerson();
			n.setPostedBy(personFromDbToJson.apply(person));
			n.setPosted(db.getPosted());

			n.setTitle(db.getTitle());
			n.setViews(db.getViews());

			List<String> visibleTo = new ArrayList<>();
			Set<DbPersonType> visibleToPersonTypes = db.getVisibleToPersonTypes();
			if (visibleToPersonTypes != null) {
	            for (DbPersonType t : visibleToPersonTypes) {
	                visibleTo.add(t.getName());
	            }
			}
            n.setVisibleTo(visibleTo);
			
            List<DbNewsComment> dbNewsComments = db.getComments();
            if (dbNewsComments != null) {
                List<NewsComment> comments = new ArrayList<>();
                for (DbNewsComment dbnc : dbNewsComments) {
                    comments.add(newsCommentFromDbToJson.apply(dbnc));
                }
                n.setComments(comments);
            }
			return n;
		}
	}
	
    public class JsonToDbConverter {
		JsonToDbConverter() {
		}
		
		public DbNews apply(DbNews db, News p) {
		    db.setId(p.getId());
			if (!Strings.isNullOrEmpty(p.getTitle())) {
	            db.setTitle(p.getTitle());
			}
			if (!Strings.isNullOrEmpty(p.getDescription())) {
			    db.setDescription(p.getDescription());
			}
			db.setExpired(ConverterUtils.asTimestamp(p.getExpired()));
			Person postedBy = p.getPostedBy();
			if (postedBy != null 
			    && !Strings.isNullOrEmpty(postedBy.getEmail()) 
			    && db.getPerson() == null) {
			    // Only update the person if it hadn't been set already.
			    db.setPersonId(personService.findByEmail(postedBy.getEmail()));
			}
			db.setPosted(ConverterUtils.asTimestamp(p.getPosted()));
            db.setViews(p.getViews());

            List<String> visibleToNames = p.getVisibleTo();
			if (visibleToNames == null) { 
			    visibleToNames = Collections.emptyList();
			}
			Set<DbPersonType> visibleToTypes = new HashSet<>();
			for (String name : visibleToNames) {
			    DbPersonType t = personTypeService.findByName(name);
			    if (t == null) continue;
			    visibleToTypes.add(t);
			}
			db.setVisibleToPersonTypes(visibleToTypes);
			return db;
		}
	}
		
	@Bean
	public NewsFromDbToJson newsFromDbToJson() {
		return new NewsFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter newsFromJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private NewsConverters() {
	}
}

