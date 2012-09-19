package com.binomed.sqli.gwt.client;

import java.util.Date;

import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.CalendarActivity.Display;
import com.binomed.sqli.gwt.client.presenter.HomeActivity;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.resources.ProjectResources;
import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.google.api.gwt.client.GoogleApiRequestTransport;
import com.google.api.gwt.client.OAuth2Login;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarAuthScope;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class ClientFactory implements IClientFactory {

	// private static final Logger LOGGER = Logger.getLogger("ClientFacotry");

	private final EventBus eventBus = new SimpleEventBus();
	private final SqliServiceAsync rpcService;
	private final PlaceController placeControler = new PlaceController(eventBus);
	private final HasWidgets container;
	private final ActivityManager activityManager;
	private final HomePresenter homePresenter;
	private final SqliRequestFactory requestFactory;

	// private static final String CALENDAR_ID = "jean.francois.garreau@gmail.com";
	private static final String CALENDAR_ID = "ahkgurr9feh3leouhc2na4hbvo@group.calendar.google.com";
	private static final String CLIENT_ID = "1092752234333-altm4129jd9js6o31hgrrfq6qc028k4e.apps.googleusercontent.com";
	private static final String API_KEY = "AIzaSyCZCYiIpkDdgzmXVeJihy3p-lEj33m6WtQ";
	private static final String APPLICATION_NAME = "CalendarSample/1.0";
	private static final Calendar calendar = GWT.create(Calendar.class);

	public ClientFactory() {
		super();

		ProjectResources.instance.css().ensureInjected();

		this.rpcService = GWT.create(SqliService.class);
		this.requestFactory = GWT.create(SqliRequestFactory.class);
		this.requestFactory.initialize(eventBus);
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

		// We init the calendar api
		calendar.initialize(eventBus, //
				new GoogleApiRequestTransport(APPLICATION_NAME, API_KEY));

	}

	public EventBus getEventBusHandler() {
		return eventBus;
	}

	@Override
	public SqliServiceAsync getService() {
		return rpcService;
	}

	@Override
	public PlaceController getPlaceControler() {
		return placeControler;
	}

	@Override
	public void registerMainPanel(AcceptsOneWidget mainPanel) {
		activityManager.setDisplay(mainPanel);
	}

	@Override
	public void showMessage(String message) {
		homePresenter.eventClick(message);

	}

	@Override
	public SqliRequestFactory getRequestFactory() {
		return requestFactory;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public void showLoadMessage(String message) {
		homePresenter.showLoadMessage(message);
	}

	@Override
	public void hideLoadMessage() {
		homePresenter.hideLoadMessage();

	}

	@Override
	public void getListEvents(int start, int number, Receiver<Events> callBack) {
		calendar.events().list(CALENDAR_ID) //
				.setMaxResults(number) //
				.setTimeMin(DateTimeFormat.getFormat(Display.DATE_TIME_FORMAT).format(new Date()))//
				.fire(callBack);

	}

	/**
	 * Method for authantification for adding event in user calendar
	 */
	private void login() {
		OAuth2Login.get().authorize(CLIENT_ID, //
				CalendarAuthScope.CALENDAR,//
				new Callback<Void, Exception>() {
					@Override
					public void onSuccess(Void v) {

					}

					@Override
					public void onFailure(Exception e) {
						GWT.log("Auth failed:", e);
					}
				});
	}

	@Override
	public void getEventDetails(String eventId, Receiver<Event> callBack) {
		calendar.events().get(CALENDAR_ID, eventId).fire(callBack);

	}
}
