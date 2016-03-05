package org.spring2885.server.db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.spring2885.model.News;
import org.spring2885.server.api.TestConfig;
import org.spring2885.server.db.model.DbNews;
import org.spring2885.server.db.model.NewsConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.base.Function;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class NewsConverterTest {
    @Autowired
    NewsConverters.FromDbToJson fromDbToJson;
    @Autowired
    NewsConverters.JsonToDbConverter fromJsonToDb;

    public NewsConverterTest(){}
	
	@Test
	public void testFromDbToJson(){
		DbNews dbp = new DbNews();
		News p = new News();
		p = fromDbToJson.apply(dbp);
	}
	
	@Test
	public void testFromJsonToDb(){
		News p = new News();
		DbNews dbp = new DbNews();
		dbp = fromJsonToDb.apply(p);
	}
}
