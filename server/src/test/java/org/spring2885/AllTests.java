package org.spring2885;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.spring2885.server.ServerApplicationTests;
import org.spring2885.server.api.JobsApiTest;
import org.spring2885.server.api.PersonsApiTest;
import org.spring2885.server.db.service.JobConverterTest;
import org.spring2885.server.db.service.JobServiceTest;
import org.spring2885.server.db.service.LanguageServiceTest;
import org.spring2885.server.db.service.PersonConverterTest;
import org.spring2885.server.db.service.PersonServiceTest;
import org.spring2885.server.db.service.SocialServiceServiceTest;

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
	PersonsApiTest.class,
	PersonServiceTest.class,
	PersonConverterTest.class,
	SocialServiceServiceTest.class,
})
@RunWith(Suite.class)
public class AllTests {
}
