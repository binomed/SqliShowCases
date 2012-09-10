package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.view.HomeView;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;

public class ClientFactory implements IClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final SqliServiceAsync rpcService;
	private final PlaceController placeControler = new PlaceController(eventBus);
	private final HasWidgets container;
	private final ActivityManager activityManager;

	public ClientFactory() {
		super();
		// this.eventBus = new SimpleEventBus();
		this.rpcService = GWT.create(SqliService.class);
		this.container = RootLayoutPanel.get();
		// this.placeControler = new PlaceController(eventBus);

		// Start ActivityMapper for the main widget
		activityManager = new ActivityManager(new AppAcitivityMapper(this), this.eventBus);
		HomeView homeView = new HomeView();
		// SimplePanel rootPanel = new SimplePanel();
		activityManager.setDisplay(homeView.registerMainPanel());

		// Start the place history handler with our place history mapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeControler, eventBus, new LoginPlace());

		this.container.add(homeView);
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();

	}

	public EventBus getEventBusHandler() {
		return eventBus;
	}

	public SqliServiceAsync getService() {
		return rpcService;
	}

	public PlaceController getPlaceControler() {
		return placeControler;
	}

	public void registerMainPanel(AcceptsOneWidget mainPanel) {
		activityManager.setDisplay(mainPanel);
	}

}