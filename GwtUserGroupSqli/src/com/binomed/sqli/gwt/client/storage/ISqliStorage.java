package com.binomed.sqli.gwt.client.storage;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.bradrydzewski.gwt.calendar.client.Appointment;

public interface ISqliStorage {

	void saveUser(SqliUserProxy user);

	String getLastUserLogin();

	void removeUserLogin();

	List<Appointment> getListEvents();

	void saveListEvents(List<Appointment> events);

}
