package org.spring2885.server.db.model;

import org.spring2885.model.SocialService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
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
	
    public class JsonToDbConverter {
		
		JsonToDbConverter() {
		}
		
		public DbSocialService apply(DbSocialService db, SocialService p) {
			db.setName(db.getName());
			db.setUrl(p.getUrl());
			return db;
		}
	}
	
	
	@Bean
	public SocialServiceFromDbToJson dbToJsonConverter() {
		return new SocialServiceFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter SocialServiceJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private SocialServiceConverters() {
	}
}
