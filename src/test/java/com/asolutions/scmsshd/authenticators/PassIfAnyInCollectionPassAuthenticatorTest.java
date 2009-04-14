package com.asolutions.scmsshd.authenticators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.junit.Test;

import com.asolutions.MockTestCase;


public class PassIfAnyInCollectionPassAuthenticatorTest extends MockTestCase {
	
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	
	@Test
	public void testFailsWithNothingInChain() throws Exception {
		assertNull(new PassIfAnyInCollectionPassAuthenticator().authenticate(USERNAME, PASSWORD));
	}
	
	@Test
	public void testPassIfAnyPass() throws Exception {

		final IPasswordAuthenticator failsAuth = context.mock(IPasswordAuthenticator.class, "failsAuth");
		final IPasswordAuthenticator passesAuth = context.mock(IPasswordAuthenticator.class, "passesAuth");
		
		checking(new Expectations(){{
			allowing(failsAuth).authenticate(USERNAME, PASSWORD);
			will(returnValue(null));
			allowing(passesAuth).authenticate(USERNAME, PASSWORD);
			will(returnValue(true));
		}});
		
		PassIfAnyInCollectionPassAuthenticator auth = new PassIfAnyInCollectionPassAuthenticator();
		ArrayList authList = new ArrayList();
		authList.add(failsAuth);
		authList.add(passesAuth);
		authList.add(failsAuth);
		auth.setAuthenticators(authList);
		assertNotNull(auth.authenticate(USERNAME, PASSWORD));
	}
	
	@Test
	public void testFailIfNonePass() throws Exception {
		final IPasswordAuthenticator failsAuth = context.mock(IPasswordAuthenticator.class, "failsAuth");
		
		checking(new Expectations(){{
			allowing(failsAuth).authenticate(USERNAME, PASSWORD);
			will(returnValue(null));
		}});
		
		PassIfAnyInCollectionPassAuthenticator auth = new PassIfAnyInCollectionPassAuthenticator();
		ArrayList authList = new ArrayList();
		authList.add(failsAuth);
		authList.add(failsAuth);
		auth.setAuthenticators(authList);
		assertNull(auth.authenticate(USERNAME, PASSWORD));
	}

}