package org.spring2885.server.db.model;

import org.spring2885.model.News;
import org.spring2885.server.db.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

@Component
public final class NewsConverters {
    @Autowired
    private PersonService personService;

    public class FromDbToJson implements Function<DbNews, News> {
		
		@Override
		public News apply(DbNews db) {
			News n = new News();
			n.setId(db.getId());
			n.setDescription(db.getDescription());
			n.setExpired(db.getExpired());
			DbPerson person = db.getPerson();
			if (person != null) {
	            n.setPostedBy(person.getEmail());
			}
			n.setPosted(db.getPosted());
			n.setTitle(db.getTitle());
			n.setViews(db.getViews());
			
			return n;
		}
	}
	
    public class JsonToDbConverter implements Function<News, DbNews> {
		private Supplier<DbNews> dbSupplier = Suppliers.ofInstance(new DbNews());
		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbNews(DbNews db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		@Override
		public DbNews apply(News p) {
			DbNews db = dbSupplier.get();
			db.setId(p.getId());
			db.setTitle(p.getTitle());
			db.setDescription(p.getDescription());
			db.setExpired(asSqlDate(p.getExpired()));
			db.setPersonId(personService.findByEmail(p.getPostedBy()));
			db.setPosted(asSqlDate(p.getPosted()));
			db.setTitle(db.getTitle());
			db.setViews(p.getViews());
			return db;
		}
	}
	
	private static java.sql.Date asSqlDate(java.util.Date d) {
		if (d == null) {
			return null;
		}
		return new java.sql.Date(d.getTime());
	}
	
	@Bean
	public FromDbToJson fromDbToJson() {
		return new FromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter fromJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private NewsConverters() {
	}
}

