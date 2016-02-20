package org.spring2885.server.db.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.spring2885.model.Person;
import org.spring2885.model.SocialConnection;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public final class PersonConverters {
	private static class FromDbToJson implements Function<DbPerson, Person> {

		@Override
		public Person apply(DbPerson db) {
			Person p = new Person();
			p.setId(db.getId());
			p.setName(db.getName());
			p.setStudentId(db.getStudentId());
			p.setTitle(db.getTitle());
			p.setAboutMe(p.getAboutMe());
			p.setResumeUrl(db.getResumeURL());
			p.setImageUrl(db.getImageURL());
			p.setEmail(db.getEmail());
			p.setPhone(db.getPhone());
			p.setOccupation(db.getOccupation());
			p.setCompanyName(db.getCompanyName());
			p.setBirthdate(db.getBirthdate());
			DbPersonType personType = db.getType();
			if (personType != null) {
				p.setVariety(db.getType().getName());
			} else {
				p.setVariety(null);
			}
			p.setLastLoginDate(db.getLastLogon());
			
			// Add social networks.
			for (DbSocialConnection dbSocial : db.socialConnections()) {
				SocialConnection social = new SocialConnection();
				social.setName(dbSocial.getSocialService().getName());
				social.setUrl(dbSocial.getUrl());
				p.getSocialConnections().add(social);
			}
			
			return p;
		}
	}
	
	public static class JsonToDbConverter implements Function<Person, DbPerson> {
		private Supplier<DbPerson> dbSupplier = Suppliers.ofInstance(new DbPerson());
		private Map<String, DbSocialService> socialServices = new HashMap<>();
		private Map<String, DbPersonType> personTypes = new HashMap<>();
		
		JsonToDbConverter() {
		}
		
		public JsonToDbConverter withDbPersonSupplier(Supplier<DbPerson> dbSupplier) {
			this.dbSupplier = dbSupplier;
			return this;
		}
		
		public JsonToDbConverter withDbPerson(DbPerson db) {
			this.dbSupplier = Suppliers.ofInstance(db);
			return this;
		}
		
		public JsonToDbConverter withSocialServices(Set<DbSocialService> socialServices) {
			this.socialServices = socialServices.stream()
					.collect(Collectors.toMap(DbSocialService::getName, (s) -> s));
			return this;
		}

		public JsonToDbConverter withPersonTypes(Set<DbPersonType> personTypes) {
			this.personTypes = personTypes.stream()
					.collect(Collectors.toMap(DbPersonType::getName, (p) -> p));
			return this;
		}
		
		@Override
		public DbPerson apply(Person p) {
			DbPerson db = dbSupplier.get();
			// Leave the ID null since we're updating an existing person.
			db.setName(p.getName());
			Integer studentId = p.getStudentId();
			if (studentId != null) {
				db.setStudentId(p.getStudentId());
			} else {
				db.setStudentId(0);
			}
			db.setTitle(p.getTitle());
			db.setAboutMe(db.getAboutMe());
			db.setResumeURL(p.getResumeUrl());
			db.setImageURL(p.getImageUrl());
			db.setEmail(p.getEmail());
			db.setPhone(p.getPhone());
			db.setOccupation(p.getOccupation());
			db.setCompanyName(p.getCompanyName());
			java.util.Date birthDate = p.getBirthdate();
			if (birthDate != null) {
				db.setBirthdate(new java.sql.Date(p.getBirthdate().getTime()));
			} else {
				db.setBirthdate(null);
			}
			String personType = p.getVariety();
			if (personType != null && personTypes.containsKey(personType)) {
				db.setType(personTypes.get(personType));
			} else {
				db.setType(personTypes.get("student"));
			}
			java.util.Date lastLoginDate = p.getLastLoginDate();
			if (lastLoginDate != null) {
				db.setLastLogon(new java.sql.Date(p.getLastLoginDate().getTime()));
			} else {
				db.setLastLogon(null);
			}
			
			// Add all social connections
			for (SocialConnection social : p.getSocialConnections()) {
				DbSocialService dbService = socialServices.get(social.getName());
				if (dbService == null) {
					continue;
				}
				DbSocialConnection connection = new DbSocialConnection();
				connection.setPerson(db);
				connection.setSocialService(dbService);
				connection.setUrl(social.getUrl());
				db.socialConnections().add(connection);
			}
			return db;
		}
	}
	
	public static Function<DbPerson, Person> fromDbToJson() {
		return new FromDbToJson();
	}
	
	public static JsonToDbConverter fromJsonToDb() {
		return new JsonToDbConverter();
	}
	private PersonConverters() {}
}
