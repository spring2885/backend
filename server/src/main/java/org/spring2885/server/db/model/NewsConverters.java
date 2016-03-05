package org.spring2885.server.db.model;

import java.sql.Date;

import org.spring2885.model.News;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public final class NewsConverters {
	private static class FromDbToJson implements Function<DbNews, News> {
		
		@Override
		public News apply(DbNews db) {
			News n = new News();
			n.setId(db.getId());
			n.setNewsDescription(db.getDescription());
			n.setNewsExpired(db.getExpired());
			n.setNewsPersonId(db.getPersonId());
			n.setNewsPosted(db.getPosted());
			n.setNewsTitle(db.getTitle());
			n.setNewsViews(db.getViews());
			
			return n;
		}
	}
	
	public static class JsonToDbConverter implements Function<News, DbNews> {
		private Supplier<DbNews> dbSupplier = Suppliers.ofInstance(new DbNews());
		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbNewsSupplier(Supplier<DbNews> dbSupplier) {
			this.dbSupplier = dbSupplier;
			return this;
		}
		
		public JsonToDbConverter withDbNews(DbNews db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		@Override
		public DbNews apply(News p) {
			DbNews db = dbSupplier.get();
			db.setId(p.getId());
			db.setDescription(p.getNewsDescription());
			db.setExpired(asSqlData(p.getNewsExpired()));
			db.setPersonId(p.getNewsPersonId());
			db.setPosted(asSqlData(p.getNewsPosted()));
			db.setTitle(db.getTitle());
			db.setViews(p.getNewsViews());
			return db;
		}

		private Date asSqlData(Object newsExpired) {
			// TODO Auto-generated method stub
			return null;
		}	
	}
	
	private static java.sql.Date asSqlDate(java.util.Date d) {
		if (d == null) {
			return null;
		}
		return new java.sql.Date(d.getTime());
	}
	

		public static Function<DbNews, News> fromDbToJson() {
			return new FromDbToJson();
		}
		
		public static JsonToDbConverter fromJsonToDb() {
			return new JsonToDbConverter();
		}
		
		private NewsConverters() {
		}
	}

