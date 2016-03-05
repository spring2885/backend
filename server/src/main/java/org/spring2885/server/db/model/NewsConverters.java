package org.spring2885.server.db.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
			n.setNewsDescription(db.getNewsDescription());
			n.setNewsExpired(db.getNewsExpired());
			n.setNewsPersonId(db.getNewsPersonId());
			n.setNewsPosted(db.getNewsPosted());
			n.setNewsTitle(db.getNewsTitle());
			n.setNewsViews(db.getNewsViews());
			
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
			db.setNewsDescription(p.getNewsDescription());
			db.setNewsExpired(asSqlData(p.getNewsExpired()));
			db.setNewsPersonId(p.getNewsPersonId());
			db.setNewsPosted(asSqlData(p.getNewsPosted()));
			db.setNewsTitle(db.getNewsTitle());
			db.setNewsViews(p.getNewsViews());
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

