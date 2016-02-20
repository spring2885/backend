package org.spring2885;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.spring2885.server.ServerApplicationTests;
import org.spring2885.server.api.PersonsApiTest;
import org.spring2885.server.db.service.PersonConverterTest;
import org.spring2885.server.db.service.PersonServiceTest;
import org.spring2885.server.db.service.SocialServiceServiceTest;

@SuiteClasses({ ServerApplicationTests.class, 
				// /profiles
				PersonsApiTest.class,
				PersonServiceTest.class,
				PersonConverterTest.class,
				SocialServiceServiceTest.class
				// TODO: add your next API tests here.
				} )
@RunWith(Suite.class)
public class AllTests {
}
