package com.binomed.sqli.gwt.shared.model;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(com.binomed.sqli.gwt.server.model.SqliUser.class)
public interface SqliUserProxy extends EntityProxy {

	Long getId();

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getName();

	void setName(String name);

	boolean isContactAllowed();

	void setContactAllowed(boolean contactAllowed);

	Integer getVersion();

}
