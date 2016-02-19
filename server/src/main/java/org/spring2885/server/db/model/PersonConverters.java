package org.spring2885.server.db.model;

import org.spring2885.model.Person;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public final class PersonConverters {
	private static class FromDbToJson implements Function<DbPerson, Person> {

		@Override
		public Person apply(DbPerson db) {
			Person p = new Person();
			p.setId((long)db.getId());
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
			// TODO(rob): Fix this.
			p.setVariety(Integer.toString(db.getType()));
			p.setLastLoginDate(db.getLastLogon());
			
			return p;
		}
	}
	
	private static class FromJsonToDb implements Function<Person, DbPerson> {
		private final Supplier<DbPerson> dbSupplier;
		
		FromJsonToDb(Supplier<DbPerson> dbSupplier) {
			this.dbSupplier = dbSupplier;
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
				db.setBirthdate(new java.sql.Date(0));
			}
			try {
				db.setType(Integer.parseInt(p.getVariety()));
			} catch (NumberFormatException e) {
				db.setType(0);
			}
			java.util.Date lastLoginDate = p.getLastLoginDate();
			if (lastLoginDate != null) {
				db.setLastLogon(new java.sql.Date(p.getLastLoginDate().getTime()));
			} else {
				db.setLastLogon(new java.sql.Date(0));
			}
			
			return db;
		}
	}
	
	public static Function<DbPerson, Person> fromDbToJson() {
		return new FromDbToJson();
	}
	
	public static Function<Person, DbPerson> fromJsonToDb() {
		return fromJsonToDb(new Supplier<DbPerson>() {
			@Override public DbPerson get() {
				return new DbPerson(); 
			} 
		});
	}
	
	public static Function<Person, DbPerson> fromJsonToDb(Supplier<DbPerson> dbPersonSupplier) {
		return new FromJsonToDb(dbPersonSupplier);
	}
	
	public static Function<Person, DbPerson> fromJsonToDb(DbPerson dbPerson) {
		return new FromJsonToDb(Suppliers.ofInstance(dbPerson));
	}
	
	private PersonConverters() {}
}
