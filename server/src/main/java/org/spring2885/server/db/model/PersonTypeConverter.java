package org.spring2885.server.db.model;

import org.spring2885.model.PersonType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class PersonTypeConverter {
	@Bean
	public FromDbToJson personTypeFromDbToJson() {
		return new FromDbToJson();
	}
	 
	public static class FromDbToJson implements Function<DbPersonType, PersonType> {
		@Override
		public PersonType apply(DbPersonType db) {
			PersonType p = new PersonType();
			p.setId(db.getId());
			p.setName(db.getName());
			return p;
		}	 
	}
	 
	@Bean
	public JsonToDbConverter personTypeJsonToDb() {
		return new JsonToDbConverter();
	}

	public class JsonToDbConverter {
		 
		JsonToDbConverter() {
		}
		 
		public DbPersonType apply(DbPersonType db, PersonType p) {
			db.setId(p.getId());
			db.setName(p.getName());
			return db;
		}
		 
	}
	 
	private PersonTypeConverter() {}
}
