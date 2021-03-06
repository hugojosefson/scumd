package com.asolutions.scmsshd.ldap;

import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

public interface ILDAPAuthLookupProvider {

	SearchResult provide(String url, String username, String password, boolean promiscuous) throws NamingException;

}
