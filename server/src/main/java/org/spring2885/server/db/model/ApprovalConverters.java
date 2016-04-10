package org.spring2885.server.db.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.spring2885.model.ApprovalRequest;
import org.spring2885.model.FacultyRequest;
import org.spring2885.model.Verdict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class ApprovalConverters {
    @Autowired
    private PersonConverters.FromDbToJson personFromDbToJson;

    public class ApprovalFromDbToJson implements Function<DbApprovalRequest, ApprovalRequest> {
        @Override
        public ApprovalRequest apply(DbApprovalRequest db) {
            ApprovalRequest p = new ApprovalRequest();
            p.setActive(db.getActive());
            p.setApprovalType(db.getApprovalType());
            p.setFlaggedBy(personFromDbToJson.apply(db.getFlaggedBy()));
            p.setFlaggedNotes(db.getFlaggedNotes());
            p.setFlaggedOn(db.getFlagedOn());
            
            p.setId(db.getUuid());
            p.setItemId(db.getItemId());
            p.setItemType(db.getItemType());
            p.setVerdictBy(personFromDbToJson.apply(db.getVerdictBy()));
            p.setVerdictNotes(db.getVerdictNotes());
            p.setVerdictOn(db.getVerdictOn());

            return p;
        }    
    }

    @Bean
    public ApprovalFromDbToJson approvalFromDbToJson() {
        return new ApprovalFromDbToJson();
    }
    
    public class FacultyRequestToDbApproval {
        public DbApprovalRequest create(FacultyRequest t, DbPerson requestor) {
            UUID uuid = UUID.randomUUID();

            DbApprovalRequest db = new DbApprovalRequest();
            db.setUuid(uuid.toString());
            db.setActive(Boolean.TRUE);
            db.setApprovalType("TEACHER");
            db.setFlaggedBy(requestor);
            db.setFlagedOn(Timestamp.from(Instant.now()));
            db.setFlaggedNotes(t.getNotes());

            return db;
        }
    }
    
    @Bean
    public FacultyRequestToDbApproval facultyRequestToDbApproval() {
        return new FacultyRequestToDbApproval();
    }


    public class VerdictToDbApproval {
        public DbApprovalRequest create(DbApprovalRequest db, Verdict v, DbPerson approver) {
            db.setApproved(v.getApproved());
            db.setVerdictBy(approver);
            db.setVerdictNotes(v.getVerdictNotes());
            db.setVerdictOn(Timestamp.from(Instant.now()));
            db.setActive(Boolean.FALSE);

            return db;
        }
    }
    
    @Bean
    public VerdictToDbApproval verdictToDbApproval() {
        return new VerdictToDbApproval();
    }
}
