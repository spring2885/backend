package org.spring2885.server.db.model;

import org.spring2885.model.PersonType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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

	public class JsonToDbConverter implements Function<PersonType, DbPersonType> {
		 
		private Supplier<DbPersonType> dbSupplier = Suppliers.ofInstance(new DbPersonType());
		 
		public JsonToDbConverter() {
		}
		 
		public void withDbPersonType(DbPersonType db){
			this.dbSupplier = Suppliers.ofInstance(db);
		}
		 
		@Override
		public DbPersonType apply(PersonType p) {
			DbPersonType pT = dbSupplier.get();
			pT.setId(p.getId());
			pT.setName(p.getName());
			return pT;
		}
		 
	}
	 
	 private PersonTypeConverter() {}
}
