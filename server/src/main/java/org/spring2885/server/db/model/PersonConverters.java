package org.spring2885.server.db.model;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.spring2885.model.Person;
import org.spring2885.model.SocialConnection;
import org.spring2885.server.db.service.LanguageService;
import org.spring2885.server.db.service.person.PersonTypeService;
import org.spring2885.server.db.service.person.SocialServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

@Component
public final class PersonConverters {
    @Autowired
    private SocialServiceService socialServiceService;
    @Autowired
    private PersonTypeService personTypeService;
    @Autowired
    private LanguageService languageService;

    @Bean
    public FromDbToJson personFromDbToJson() {
        return new FromDbToJson();
    }

    public static class FromDbToJson implements Function<DbPerson, Person> {

		@Override
		public Person apply(DbPerson db) {
			Person p = new Person();
			p.setId(db.getId());
			p.setAboutMe(db.getAboutMe());
			p.setName(db.getName());
			p.setDegreeMajor(db.getDegreeMajor());
			p.setDegreeMinor(db.getDegreeMinor());
			p.setDegreeType(db.getDegreeType());
			p.setStudentId(db.getStudentId());
			p.setTitle(db.getTitle());
			p.setResumeUrl(db.getResumeURL());
			p.setImageUrl(db.getImageURL());
			p.setEmail(db.getEmail());
			p.setPhone(db.getPhone());
			p.setOccupation(db.getOccupation());
			p.setCompanyName(db.getCompanyName());
			p.setBirthdate(db.getBirthdate());
			p.setGraduationYear(db.getGraduationYear());
			DbPersonType personType = db.getType();
			if (personType != null) {
				p.setVariety(db.getType().getName());
			} else {
				p.setVariety(null);
			}
			DbLanguage lang = db.getLanguage();
			if (lang != null) {
	            p.setLang(lang.getCode());
			}
			p.setLastLoginDate(db.getLastLogon());

			// Add social networks.
			for (DbSocialConnection dbSocial : db.socialConnections()) {
				SocialConnection social = new SocialConnection();
				social.setName(dbSocial.getSocialService().getName());
				social.setUrl(dbSocial.getUrl());
				p.getSocialConnections().add(social);
			}

            p.setFacultyDepartment(db.getFacultyDepartment());
			return p;
		}
	}

    @Bean
    public JsonToDbConverter personJsonToDb() {
        return new JsonToDbConverter();
    }
    
	public class JsonToDbConverter implements Function<Person, DbPerson> {

		private Supplier<DbPerson> dbSupplier = Suppliers.ofInstance(new DbPerson());

		public JsonToDbConverter() {
		}

		public void withDbPerson(DbPerson db) {
			this.dbSupplier = Suppliers.ofInstance(db);
		}

		@Override
		public DbPerson apply(Person p) {
	        Map<String, DbSocialService> socialServices = 
	                socialServiceService.findAll().stream()
                    .collect(Collectors.toMap(DbSocialService::getName, (s) -> s));
	        Map<String, DbPersonType> personTypes = 
	                personTypeService.findAll().stream()
                    .collect(Collectors.toMap(DbPersonType::getName, (s) -> s));
	        Map<String, DbLanguage> languages = 
	                languageService.findAll().stream()
                    .collect(Collectors.toMap(DbLanguage::getCode, (s) -> s));

            DbPerson db = dbSupplier.get();
			// Leave the ID null since we're updating an existing person.
			db.setName(p.getName());
			db.setStudentId(p.getStudentId());
			db.setTitle(p.getTitle());
			db.setAboutMe(p.getAboutMe());
			db.setResumeURL(p.getResumeUrl());
			db.setImageURL(p.getImageUrl());
			db.setEmail(p.getEmail());
			db.setPhone(p.getPhone());
			db.setOccupation(p.getOccupation());
			db.setCompanyName(p.getCompanyName());
			db.setBirthdate(asSqlDate(p.getBirthdate()));
			String personType = p.getVariety();
			if (personType != null && personTypes.containsKey(personType)) {
				db.setType(personTypes.get(personType));
			} else {
			    db.setType(personTypeService.defaultType());
			}
			String languageCode = p.getLang();
			if (languageCode != null && languages.containsKey(languageCode)) {
			    db.setLanguage(languages.get(languageCode));
			} else {
			    db.setLanguage(languageService.defaultLanguage());
			}
			db.setLastLogon(asSqlDate(p.getLastLoginDate()));

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

			// student only fields
			db.setDegreeMajor(p.getDegreeMajor());
			db.setDegreeMinor(p.getDegreeMinor());
			// student/alumni shared field
			db.setGraduationYear(p.getGraduationYear());
			db.setDegreeType(p.getDegreeType());
			// faculty fields
			db.setFacultyDepartment(p.getFacultyDepartment());

			return db;
		}
	}

	private static java.sql.Date asSqlDate(java.util.Date d) {
		if (d == null) {
			return null;
		}
		return new java.sql.Date(d.getTime());
	}

	private PersonConverters() {}
}
