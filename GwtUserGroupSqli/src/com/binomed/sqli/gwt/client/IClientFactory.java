package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public interface IClientFactory {

	EventBus getEventBus();

	SqliRequestFactory getRequestFactory();

	SqliServiceAsync getService();

	PlaceController getPlaceControler();

	void registerMainPanel(AcceptsOneWidget mainPanel);

	void showMessage(String message);

}
