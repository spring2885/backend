package org.spring2885.server.db.model;

import org.spring2885.model.Language;
import org.spring2885.model.Language;
import org.spring2885.server.db.model.LanguageConverters.JsonToDbConverter;
import org.spring2885.server.db.model.LanguageConverters.LanguageFromDbToJson;
import org.springframework.context.annotation.Bean;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class LanguageConverters {

	public class LanguageFromDbToJson implements Function<DbLanguage, Language> {

		@Override
		public Language apply(DbLanguage db) {
			Language l = new Language();
			l.setCode(db.getCode());
			l.setDescription(db.getDescription());
			
			return l;
		}
}
	 public class JsonToDbConverter implements Function<Language, DbLanguage> {
		 private Supplier<DbLanguage> dbSupplier = Suppliers.ofInstance(new DbLanguage());		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbLanguage(DbLanguage db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		@Override
		public DbLanguage apply(Language p) {
			DbLanguage db = dbSupplier.get();
			db.setCode(db.getCode());
			db.setDescription(p.getDescription());
			return db;
		}
	}
	
	
	@Bean
	public LanguageFromDbToJson dbToJsonConverter() {
		return new LanguageFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter LanguageJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private LanguageConverters() {
	}
	 }