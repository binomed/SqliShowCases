package com.binomed.sqli.gwt.client.storage;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;

public interface ISqliStorage {

	void saveUser(SqliUserProxy user);

	String getLastUserLogin();

	void removeUserLogin();

}
