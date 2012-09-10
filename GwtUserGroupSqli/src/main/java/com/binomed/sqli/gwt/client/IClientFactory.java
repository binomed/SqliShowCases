package com.binomed.sqli.gwt.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface IClientFactory {

	SqliServiceAsync getService();

	PlaceController getPlaceControler();

	void registerMainPanel(AcceptsOneWidget mainPanel);

}
