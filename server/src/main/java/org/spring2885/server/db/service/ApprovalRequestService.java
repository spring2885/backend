package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbApprovalRequest;

public interface ApprovalRequestService {
    Iterable<DbApprovalRequest> findAll();
    Iterable<DbApprovalRequest> findActive();
    Iterable<DbApprovalRequest> findClosed();
    DbApprovalRequest findById(String uuid);
    DbApprovalRequest save(DbApprovalRequest approval);
}
