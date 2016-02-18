package org.spring2885.server.db.model;

import org.spring2885.model.Person;

import com.google.common.base.Function;

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

		@Override
		public DbPerson apply(Person p) {
			DbPerson db = new DbPerson();
			// Leave the ID null since we're updating an existing person.
			db.setName(p.getName());
			db.setStudentId(p.getStudentId());
			db.setTitle(p.getTitle());
			db.setAboutMe(db.getAboutMe());
			db.setResumeURL(p.getResumeUrl());
			db.setImageURL(p.getImageUrl());
			db.setEmail(p.getEmail());
			db.setPhone(p.getPhone());
			db.setOccupation(p.getOccupation());
			db.setCompanyName(p.getCompanyName());
			db.setBirthdate(new java.sql.Date(p.getBirthdate().getTime()));
			try {
				db.setType(Integer.parseInt(p.getVariety()));
			} catch (NumberFormatException e) {
				db.setType(0);
			}
			db.setLastLogon(new java.sql.Date(p.getLastLoginDate().getTime()));
			
			return db;
		}
	}
	
	public static Function<DbPerson, Person> fromDbToJson() {
		return new FromDbToJson();
	}
	
	public static Function<Person, DbPerson> fromJsonToDb() {
		return new FromJsonToDb();
	}
	
	private PersonConverters() {}
}
