package org.spring2885.server.db.model;

import java.util.Map;
import java.util.stream.Collectors;

import org.spring2885.model.News;
import org.spring2885.model.SocialService;
import org.spring2885.server.db.service.person.PersonService;
import org.spring2885.server.db.model.NewsConverters.JsonToDbConverter;
import org.spring2885.server.db.model.NewsConverters.NewsFromDbToJson;
import org.spring2885.server.db.service.SocialServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class SocialServiceConverters {


    public class SocialServiceFromDbToJson implements Function<DbSocialService, SocialService> {
		
		@Override
		public SocialService apply(DbSocialService db) {
			SocialService s = new SocialService();
			s.setId(db.getName());
			s.setUrl(db.getUrl());
			
			return s;
		}
	}
	
    public class JsonToDbConverter implements Function<SocialService, DbSocialService> {
		private Supplier<DbSocialService> dbSupplier = Suppliers.ofInstance(new DbSocialService());
		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbSocialService(DbSocialService db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		@Override
		public DbSocialService apply(SocialService p) {
			DbSocialService db = dbSupplier.get();
			db.setName(db.getName());
			db.setUrl(p.getUrl());
			return db;
		}
	}
	
	
	@Bean
	public SocialServiceFromDbToJson socialServiceFromDbToJson() {
		return new SocialServiceFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter newsFromJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private SocialServiceConverters() {
	}
}
