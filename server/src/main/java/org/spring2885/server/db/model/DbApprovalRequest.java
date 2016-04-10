package org.spring2885.server.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="approval_request")
@EntityListeners(AuditingEntityListener.class)
public class DbApprovalRequest {
    @Id
    private String uuid;

    // Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
    private Boolean active;

    private String approvalType;
    private String itemType;
    private Long itemId;
    
    private Timestamp flaggedOn;
    @JoinColumn(name="flagged_by")
    @ManyToOne(fetch=FetchType.EAGER)
    private DbPerson flaggedBy;
    private String flaggedNotes;

    // Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
    private Boolean approved;
    private Timestamp verdictOn;
    @JoinColumn(name="verdict_by")
    @ManyToOne(fetch=FetchType.EAGER)
    private DbPerson verdictBy;
    private String verdictNotes;

    // Auditing Columns
    @Version
    private Long version;

    @CreatedDate
    private java.util.Date creationTime;
    
    @LastModifiedDate
    private java.util.Date modificationTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Timestamp getFlaggedOn() {
        return flaggedOn;
    }

    public void setFlagedOn(Timestamp flaggedOn) {
        this.flaggedOn = flaggedOn;
    }

    public DbPerson getFlaggedBy() {
        return flaggedBy;
    }

    public void setFlaggedBy(DbPerson flaggedBy) {
        this.flaggedBy = flaggedBy;
    }

    public String getFlaggedNotes() {
        return flaggedNotes;
    }

    public void setFlaggedNotes(String flaggedNotes) {
        this.flaggedNotes = flaggedNotes;
    }

    public Timestamp getVerdictOn() {
        return verdictOn;
    }

    public void setVerdictOn(Timestamp verdictOn) {
        this.verdictOn = verdictOn;
    }

    public DbPerson getVerdictBy() {
        return verdictBy;
    }

    public void setVerdictBy(DbPerson verdictBy) {
        this.verdictBy = verdictBy;
    }

    public String getVerdictNotes() {
        return verdictNotes;
    }

    public void setVerdictNotes(String verdictNotes) {
        this.verdictNotes = verdictNotes;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public java.util.Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(java.util.Date creationTime) {
        this.creationTime = creationTime;
    }

    public java.util.Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(java.util.Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

}
