package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

public interface IClientFactory {

	EventBus getEventBus();

	SqliRequestFactory getRequestFactory();

	SqliServiceAsync getService();

	PlaceController getPlaceControler();

	SqliUserProxy getConnectedUser();

	void registerMainPanel(AcceptsOneWidget mainPanel);

	void getListEvents(int start, int number, Receiver<Events> callBack);

	void getEventDetails(String eventId, Receiver<Event> callBack);

	Place getCurrentPlace();

	void updatePlace(Place place);

	void addEventToCalendar(Event event);

}
