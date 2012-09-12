package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.HomeActivity;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.resources.ProjectResources;
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

	// private static final Logger LOGGER = Logger.getLogger("ClientFacotry");

	private final EventBus eventBus = new SimpleEventBus();
	private final SqliServiceAsync rpcService;
	private final PlaceController placeControler = new PlaceController(eventBus);
	private final HasWidgets container;
	private final ActivityManager activityManager;
	private final HomePresenter homePresenter;

	public ClientFactory() {
		super();

		ProjectResources.instance.css().ensureInjected();

		this.rpcService = GWT.create(SqliService.class);
		this.container = RootLayoutPanel.get();

		// Start ActivityMapper for the main widget
		activityManager = new ActivityManager(new AppAcitivityMapper(this), this.eventBus);
		homePresenter = new HomeActivity(this, this.container);
		// SimplePanel rootPanel = new SimplePanel();
		activityManager.setDisplay(homePresenter.getMainPanel());

		// Start the place history handler with our place history mapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeControler, eventBus, new LoginPlace());

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

	public void showMessage(String message) {
		homePresenter.eventClick(message);

	}

}
