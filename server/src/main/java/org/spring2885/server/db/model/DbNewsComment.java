package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Cacheable
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="news_comment")
public class DbNewsComment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JoinColumn(name="news_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private DbNews news;
    
    private String commentText;
    private Date commentTimestamp;
    
    @JoinColumn(name="person_id")
    @ManyToOne(fetch=FetchType.LAZY)
    private DbPerson person;
    
    // Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
    private Boolean active;
    
    // Mark this as not insertable so the default database value will be used.
    @Column(nullable = false, insertable=false, columnDefinition = "TINYINT", length = 1)
    private Boolean abuse;
    
    @Version
	private Long version;

	@CreatedDate
	private java.util.Date creationTime;
		
	@LastModifiedDate
	private java.util.Date modificationTime;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedBy
	private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DbNews getNews() {
        return news;
    }

    public void setNews(DbNews news) {
        this.news = news;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentTimestamp() {
        return commentTimestamp;
    }

    public void setCommentTimestamp(Date commentTimestamp) {
        this.commentTimestamp = commentTimestamp;
    }

    public DbPerson getPerson() {
        return person;
    }

    public void setPerson(DbPerson person) {
        this.person = person;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAbuse() {
        return abuse;
    }

    public void setAbuse(Boolean abuse) {
        this.abuse = abuse;
    }
    
    public java.util.Date getCreationTime(){
		return creationTime;
	}
	
	public java.util.Date getModificationTime(){
		return modificationTime;
	}
	
	public Long getVersion(){
		return version;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public String getLastModifiedBy(){
		return modifiedBy;
	}
    
}
