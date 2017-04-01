package org.spring2885.server.db.model;

import org.spring2885.model.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

@Component
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
	 public class JsonToDbConverter {
		private Supplier<DbLanguage> dbSupplier = Suppliers.ofInstance(new DbLanguage());		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbLanguage(DbLanguage db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		public DbLanguage apply(Language p) {
			DbLanguage db = dbSupplier.get();
			db.setCode(db.getCode());
			db.setDescription(p.getDescription());
			return db;
		}

		public DbLanguage apply(DbLanguage db, Language lang) {
			db.setCode(db.getCode());
			db.setDescription(lang.getDescription());
			return db;
		}
	}
	
	
	@Bean
	public LanguageFromDbToJson languageDbToJson() {
		return new LanguageFromDbToJson();
	}
	
	@Bean
	public JsonToDbConverter languageJsonToDb() {
		return new JsonToDbConverter();
	}
	
	private LanguageConverters() {
	}
	 }