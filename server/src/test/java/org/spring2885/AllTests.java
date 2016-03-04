package org.spring2885;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.spring2885.server.ServerApplicationTests;
import org.spring2885.server.api.*;
import org.spring2885.server.db.service.*;

@SuiteClasses({ ServerApplicationTests.class, 
				// /profiles
				/*PersonsApiTest.class,
				PersonServiceTest.class,
				PersonConverterTest.class,
				SocialServiceServiceTest.class,
				// TODO: add your next API tests here.
				 */
				NewsApiTest.class,
				NewsServiceTest.class,
				NewsConverterTest.class
				} )
@RunWith(Suite.class)
public class AllTests {
}
