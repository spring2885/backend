package org.spring2885;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.spring2885.server.ServerApplicationTests;
import org.spring2885.server.api.JobsApiTest;
import org.spring2885.server.api.NewsApiTest;
import org.spring2885.server.api.PersonsApiTest;
import org.spring2885.server.db.service.JobConverterTest;
import org.spring2885.server.db.service.JobServiceTest;
import org.spring2885.server.db.service.LanguageServiceTest;
import org.spring2885.server.db.service.NewsConverterTest;
import org.spring2885.server.db.service.NewsServiceTest;
import org.spring2885.server.db.service.PersonConverterTest;
import org.spring2885.server.db.service.PersonServiceTest;
import org.spring2885.server.db.service.SocialServiceServiceTest;
import org.spring2885.server.db.service.TokenServiceTest;
import org.spring2885.server.db.service.search.SearchParserTest;

/**
 * Lists each test class in the project.
 * Please add yours in alphabetical order. 
 */
@SuiteClasses({
    // Spring Boot Tests
    ServerApplicationTests.class, 
    // API Tests
    JobsApiTest.class,
    JobServiceTest.class,
    JobConverterTest.class,
    LanguageServiceTest.class,
    NewsApiTest.class,
    NewsConverterTest.class,
    NewsServiceTest.class,
    PersonsApiTest.class,
    PersonServiceTest.class,
    PersonConverterTest.class,
    SearchParserTest.class,
    SocialServiceServiceTest.class,
    TokenServiceTest.class,
})
@RunWith(Suite.class)
public class AllTests {
}
