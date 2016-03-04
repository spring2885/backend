package org.spring2885.server.db.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class DbNews {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="news_title")
	private String newsTitle;
	@Column(name="news_description")
	private String newsDescription;
	@Column(name="news_posted")
	private Date newsPosted;
	@Column(name="news_expired")
	private Date newsExpired;
	@Column(name="news_person_id")
	private long newsPersonId;
	@Column(name="news_views")
	private long newsViews;
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNewsTitle() {
		return newsTitle;
	}


	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}


	public String getNewsDescription() {
		return newsDescription;
	}


	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
	}


	public Date getNewsPosted() {
		return newsPosted;
	}


	public void setNewsPosted(Date newsPosted) {
		this.newsPosted = newsPosted;
	}


	public Date getNewsExpired() {
		return newsExpired;
	}


	public void setNewsExpired(Date newsExpired) {
		this.newsExpired = newsExpired;
	}


	public long getNewsPersonId() {
		return newsPersonId;
	}


	public void setNewsPersonId(long newsPersonId) {
		this.newsPersonId = newsPersonId;
	}


	public long getNewsViews() {
		return newsViews;
	}


	public void setNewsViews(long newsViews) {
		this.newsViews = newsViews;
	}


	@Override
	public String toString() {
		return "DbNews [id=" + id + ", newsTitle=" + newsTitle + ", newsDescription=" + newsDescription
				+ ", newsPosted=" + newsPosted + ", newsExpired=" + newsExpired + ", newsPersonId=" + newsPersonId
				+ ", newsViews=" + newsViews + "]";
	}
	
	
}
