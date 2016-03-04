package org.spring2885.server.db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.spring2885.model.News;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.NewsConverters;

import com.google.common.base.Function;

@RunWith(JUnit4.class)
public class NewsConverterTest {
	public NewsConverterTest(){}
	
	@Test
	public void testFromDbToJson(){
		Function<DbNews, News> dtoj = NewsConverters.fromDbToJson();
		DbNews dbp = new DbNews();
		News p = new News();
		p = dtoj.apply(dbp);
	}
	
	@Test
	public void testFromJsonToDb(){
		Function<News, DbNews> jtod = NewsConverters.fromJsonToDb();
		News p = new News();
		DbNews dbp = new DbNews();
		dbp = jtod.apply(p);
	}
}
