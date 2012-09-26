package com.binomed.sqli.gwt.client.storage;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.storage.client.Storage;

public class ImplSqliStorage implements ISqliStorage {

	private final Storage stockStore;

	private static final String KEY_USER_LOGIN = "login";
	private static final String KEY_EVENTS_NUMBER = "eventNumber";
	private static final String KEY_EVENT = "event.";
	private static final String KEY_EVENT_NAME = "name";
	private static final String KEY_EVENT_DESCRIPTION = "description";
	private static final String KEY_EVENT_START_DATE = "startDate";
	private static final String KEY_EVENT_END_DATE = "endDate";
	private static final String KEY_EVENT_PLACE = "place";

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

	@Override
	public List<Appointment> getListEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveListEvents(List<Appointment> events) {
		// TODO Auto-generated method stub

	}

}
