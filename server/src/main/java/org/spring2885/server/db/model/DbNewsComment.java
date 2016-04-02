package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
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
    
}
