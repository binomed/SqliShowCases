package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

public interface IClientFactory {

	EventBus getEventBus();

	SqliRequestFactory getRequestFactory();

	SqliServiceAsync getService();

	PlaceController getPlaceControler();

	void registerMainPanel(AcceptsOneWidget mainPanel);

	void showMessage(String message);

	void showLoadMessage(String message);

	void hideLoadMessage();

	void getListEvents(int start, int number, Receiver<Events> callBack);

	void getEventDetails(String eventId, Receiver<Event> callBack);

}
