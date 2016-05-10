package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbTemplate;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<DbTemplate, String> {

}
