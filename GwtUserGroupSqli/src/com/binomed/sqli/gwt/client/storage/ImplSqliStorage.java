package com.binomed.sqli.gwt.client.storage;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.storage.client.Storage;

public class ImplSqliStorage implements ISqliStorage {

	private final Storage stockStore;

	private static final String KEY_USER_LOGIN = "login";

	public ImplSqliStorage() {
		super();
		stockStore = Storage.getLocalStorageIfSupported();
	}

	@Override
	public void saveUser(SqliUserProxy user) {
		if (stockStore != null) {
			stockStore.setItem(KEY_USER_LOGIN, user.getEmail());
		}

	}

	@Override
	public String getLastUserLogin() {
		String login = null;
		if (stockStore != null) {
			login = stockStore.getItem(KEY_USER_LOGIN);
		}
		return login;
	}

	@Override
	public void removeUserLogin() {
		if (stockStore != null) {
			stockStore.removeItem(KEY_USER_LOGIN);
		}
	}

}
