package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.html5.storage.ISqliStorage;
import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author jfgarreau
 * 
 *         Global Factory
 */
public interface IClientFactory {

	/**
	 * @return the event bus for event management
	 */
	EventBus getEventBus();

	/**
	 * @return the factory requestFactory
	 */
	SqliRequestFactory getRequestFactory();

	/**
	 * @return the rpc service
	 */
	SqliServiceAsync getService();

	/**
	 * @return the place controler for place management
	 */
	PlaceController getPlaceControler();

	/**
	 * @return the curent connected user
	 */
	SqliUserProxy getConnectedUser();

	/**
	 * @return the last place visited
	 */
	Place getCurrentPlace();

	/**
	 * @return the localstorage
	 */
	ISqliStorage getAppStorage();

	/**
	 * @param mainPanel
	 *            gives the main panel for activity management
	 */
	void registerMainPanel(AcceptsOneWidget mainPanel);

	/**
	 * List all the google events according to number and start
	 * 
	 * @param start
	 * @param number
	 * @param callBack
	 */
	void getListEvents(int start, int number, Receiver<Events> callBack);

	/**
	 * Give the details of google event
	 * 
	 * @param eventId
	 * @param callBack
	 */
	void getEventDetails(String eventId, Receiver<Event> callBack);

	/**
	 * Change the curent place
	 * 
	 * @param place
	 */
	void updatePlace(Place place);

	/**
	 * Add the curent event to the calendar of user (according to website session)
	 * 
	 * @param event
	 */
	void addEventToCalendar(Event event);

	/**
	 * @return <code>true</code> if the application is onLine. <code>false</code> else
	 */
	boolean isConnect();

}
