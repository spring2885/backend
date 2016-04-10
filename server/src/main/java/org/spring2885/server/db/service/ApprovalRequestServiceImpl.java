package org.spring2885.server.db.service;

import org.spring2885.server.db.model.DbApprovalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("approvalRequestService")
@Transactional
public class ApprovalRequestServiceImpl implements ApprovalRequestService {
    private final ApprovalRequestRepository repository;
    
    @Autowired
    ApprovalRequestServiceImpl(ApprovalRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<DbApprovalRequest> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<DbApprovalRequest> findActive() {
        return repository.findAllByActive(true);
    }

    @Override
    public Iterable<DbApprovalRequest> findClosed() {
        return repository.findAllByActive(false);
    }

    @Override
    public DbApprovalRequest findById(String uuid) {
        return repository.findOne(uuid);
    }

    @Override
    public DbApprovalRequest save(DbApprovalRequest approval) {
        return repository.save(approval);
    }

}
