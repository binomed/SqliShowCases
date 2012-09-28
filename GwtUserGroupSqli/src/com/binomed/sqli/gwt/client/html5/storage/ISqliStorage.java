package com.binomed.sqli.gwt.client.html5.storage;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.api.gwt.services.calendar.shared.model.Event;

public interface ISqliStorage {

	void saveUser(SqliUserProxy user);

	String getLastUserLogin();

	void removeUserLogin();

	List<Event> getListEvents();

	int getNbEvents();

	void saveListEvents(List<Event> events);

	Event getEvent(int i);

	int getEventNumber(String idEvent);

}
