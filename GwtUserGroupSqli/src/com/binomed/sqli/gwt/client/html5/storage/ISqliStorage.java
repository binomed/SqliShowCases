package com.binomed.sqli.gwt.client.html5.storage;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.api.gwt.services.calendar.shared.model.Event;

/**
 * @author jfgarreau
 * 
 */
public interface ISqliStorage {

	/**
	 * Save the curent user
	 * 
	 * @param user
	 */
	void saveUser(SqliUserProxy user);

	/**
	 * Gives user last login
	 * 
	 * @return
	 */
	String getLastUserLogin();

	/**
	 * Give user first name and last name
	 * 
	 * @return
	 */
	String[] getUserName();

	/**
	 * Removes user from localStorage
	 */
	void removeUserLogin();

	/**
	 * Gives all events
	 * 
	 * @return
	 */
	List<Event> getListEvents();

	/**
	 * Gives the number of events
	 * 
	 * @return
	 */
	int getNbEvents();

	/**
	 * Add the current events to local storage
	 * 
	 * @param events
	 */
	void saveListEvents(List<Event> events);

	/**
	 * Gives an event
	 * 
	 * @param i
	 * @return
	 */
	Event getEvent(int i);

	/**
	 * Give the number of event according to it's id
	 * 
	 * @param idEvent
	 * @return
	 */
	int getEventNumber(String idEvent);

	/**
	 * removes all events
	 */
	void clearEvents();

}
