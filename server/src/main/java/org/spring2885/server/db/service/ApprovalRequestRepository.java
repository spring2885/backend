package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbApprovalRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ApprovalRequestRepository 
    extends CrudRepository<DbApprovalRequest, String>, JpaSpecificationExecutor<DbApprovalRequest> {

    Iterable<DbApprovalRequest> findAllByActive(boolean active);
}
