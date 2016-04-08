package org.spring2885.server.db.service.person;

import org.spring2885.server.db.model.DbPerson;
import org.spring2885.server.db.model.DbPersonType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return DbPerson.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DbPerson person = (DbPerson) target;
		DbPersonType personType = person.getType();
		if (personType.getId() == 0) {
			errors.rejectValue("PersonType", "Not a Teacher.");
		}
		else if (personType.getId() == 1) {
			errors.rejectValue("PersonType", "Not a Teacher.");
		}
		else if (personType.getId() == null) {
			errors.rejectValue("PersonType", "PersonType missing.");
		}
	}
	
	public void validate(Object target, String keyword, Errors errors) {
		String secretKeyword = "Fjp77Ux1C8";
		DbPerson person = (DbPerson) target;
		DbPersonType personType = person.getType();
		
		if (!keyword.equals(secretKeyword) || keyword.isEmpty()) {
			errors.rejectValue("Keyword", "Incorrect keyword.");
		}
		else {
			personType.setId(2);
			person.setType(personType);
		}
	}

}
