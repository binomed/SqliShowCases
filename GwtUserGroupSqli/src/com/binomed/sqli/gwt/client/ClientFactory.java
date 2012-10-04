package com.binomed.sqli.gwt.client;

import java.util.Date;
import java.util.logging.Logger;

import com.binomed.sqli.gwt.client.event.ui.ConnectionEvent;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserDisconnectedEvent;
import com.binomed.sqli.gwt.client.handler.ui.ConnectionHandler;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
import com.binomed.sqli.gwt.client.handler.workflow.UserDisconnectedHandler;
import com.binomed.sqli.gwt.client.html5.offline.SqliOfflineManagement;
import com.binomed.sqli.gwt.client.html5.storage.ISqliStorage;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.CalendarActivity.Display;
import com.binomed.sqli.gwt.client.presenter.HomeActivity;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.resources.ProjectResources;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.shared.SqliRequestFactory;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.ModalFooter;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.google.api.gwt.client.GoogleApiRequestTransport;
import com.google.api.gwt.client.OAuth2Login;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarAuthScope;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarListContext.ListRequest.MinAccessRole;
import com.google.api.gwt.services.calendar.shared.Calendar.EventsContext;
import com.google.api.gwt.services.calendar.shared.model.CalendarList;
import com.google.api.gwt.services.calendar.shared.model.CalendarListEntry;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author jfgarreau
 * 
 */
public class ClientFactory implements IClientFactory //
		, UserDisconnectedHandler //
		, UserConnectedHandler //
		, ConnectionHandler //
{

	private static final Logger LOGGER = Logger.getLogger("ClientFacotry");

	private final EventBus eventBus = new SimpleEventBus();
	private final SqliServiceAsync rpcService;
	private final PlaceController placeControler = new PlaceController(eventBus);
	private final HasWidgets container;
	private final ActivityManager activityManager;
	private final HomePresenter homePresenter;
	private final SqliRequestFactory requestFactory;
	private final ISqliStorage sqliStorage;

	private static final String LOCALHOST_URL = "http://127.0.0.1:8888/";
	private static final String CALENDAR_ID = "d67t1n4ijavur1igfo6ramp1ds@group.calendar.google.com";
	private static final String CLIENT_ID_LOCAL = "505522129937-slrkasjdeoicotmtipcmdeg479sbucb9.apps.googleusercontent.com";
	private static final String CLIENT_ID_APPENGINE = "505522129937.apps.googleusercontent.com";
	private final String CLIENT_ID;
	private static final String API_KEY = "AIzaSyACDYw9UQ6LewPFzGRmrzb50tWdf-h3MCw";
	private static final String APPLICATION_NAME = "CalendarSample/1.0";
	private static final Calendar calendar = GWT.create(Calendar.class);

	private SqliUserProxy userConected;
	private Place place;
	private boolean connected;

	public ClientFactory() {
		super();

		ProjectResources.instance.css().ensureInjected();

		this.sqliStorage = GWT.create(ISqliStorage.class);
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

		// Register curent events
		eventBus.addHandler(UserConnectedEvent.TYPE, this);
		eventBus.addHandler(UserDisconnectedEvent.TYPE, this);
		eventBus.addHandler(ConnectionEvent.TYPE, this);

		SqliOfflineManagement.checkConnection(this);

		// Management of google keys according to host
		this.CLIENT_ID = LOCALHOST_URL.equals(com.google.gwt.core.client.GWT.getHostPageBaseURL()) ? CLIENT_ID_LOCAL : CLIENT_ID_APPENGINE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getService()
	 */
	@Override
	public SqliServiceAsync getService() {
		return rpcService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getPlaceControler()
	 */
	@Override
	public PlaceController getPlaceControler() {
		return placeControler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#registerMainPanel(com.google.gwt.user.client.ui.AcceptsOneWidget)
	 */
	@Override
	public void registerMainPanel(AcceptsOneWidget mainPanel) {
		activityManager.setDisplay(mainPanel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getRequestFactory()
	 */
	@Override
	public SqliRequestFactory getRequestFactory() {
		return requestFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getEventBus()
	 */
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getListEvents(int, int, com.google.web.bindery.requestfactory.shared.Receiver)
	 */
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
	private void login(Callback<Void, Exception> callBack) {
		OAuth2Login.get().authorize(CLIENT_ID, //
				CalendarAuthScope.CALENDAR,//
				callBack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getEventDetails(java.lang.String, com.google.web.bindery.requestfactory.shared.Receiver)
	 */
	@Override
	public void getEventDetails(String eventId, Receiver<Event> callBack) {
		calendar.events().get(CALENDAR_ID, eventId).fire(callBack);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getConnectedUser()
	 */
	@Override
	public SqliUserProxy getConnectedUser() {
		return userConected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler#userConnected(com.binomed.sqli.gwt.shared.model.SqliUserProxy, boolean)
	 */
	@Override
	public void userConnected(SqliUserProxy user, boolean onLine) {
		this.userConected = user;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.handler.workflow.UserDisconnectedHandler#userDisconnected()
	 */
	@Override
	public void userDisconnected() {
		this.userConected = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getCurrentPlace()
	 */
	@Override
	public Place getCurrentPlace() {
		return place;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#updatePlace(com.google.gwt.place.shared.Place)
	 */
	@Override
	public void updatePlace(Place place) {
		this.place = place;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#addEventToCalendar(com.google.api.gwt.services.calendar.shared.model.Event)
	 */
	@Override
	public void addEventToCalendar(final Event event) {
		// We log the user with OAuth2
		login(new Callback<Void, Exception>() {

			@Override
			public void onSuccess(Void result) {
				// We list the calendars of user in order to see which one we want to add the event
				calendar.calendarList().list().setMinAccessRole(MinAccessRole.WRITER).fire(new Receiver<CalendarList>() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.google.web.bindery.requestfactory.shared.Receiver#onSuccess(java.lang.Object)
					 */
					@Override
					public void onSuccess(CalendarList response) {
						// We create the modal Dialog
						final Modal modal = new Modal(true);
						modal.setTitle(I18N.instance.calendarChoose());
						final ListBox list = new ListBox(false);
						for (CalendarListEntry calendarEntry : response.getItems()) {
							list.addItem(calendarEntry.getSummary(), calendarEntry.getId());
						}
						modal.add(list);
						ModalFooter footer = new ModalFooter();
						Button choose = new Button();
						choose.setType(ButtonType.PRIMARY);
						choose.setText(I18N.instance.calendarBtn());
						choose.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent clickEvent) {
								// When the user chosse the calendar we add it in it's calendar
								if (StringUtils.isNotEmpty(list.getValue())) {
									EventsContext eventContext = calendar.events();
									Event newEvent = eventContext.create(Event.class);
									newEvent.setSummary(event.getSummary());
									newEvent.setLocation(event.getLocation());
									newEvent.setStart(event.getStart());
									newEvent.setEnd(event.getEnd());
									newEvent.setDescription(event.getDescription());
									eventContext.insert(list.getValue(), newEvent).fire(new Receiver<Event>() {

										@Override
										public void onSuccess(Event response) {
											eventBus.fireEvent(new MessageEvent(I18N.instance.calendarAddWell(), true));

										}
									});
									modal.hide();
								} else {
									Window.alert(I18N.instance.calendarChooseCalendar());
								}

							}
						});
						footer.add(choose);
						modal.add(footer);
						modal.show();
					}
				});

			}

			/**
			 * @param reason
			 */
			@Override
			public void onFailure(Exception reason) {
				eventBus.fireEvent(new MessageEvent(I18N.instance.calendarMissingAuthorisation(), true));

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#getAppStorage()
	 */
	@Override
	public ISqliStorage getAppStorage() {
		return this.sqliStorage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.IClientFactory#isConnect()
	 */
	@Override
	public boolean isConnect() {
		return connected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.handler.ui.ConnectionHandler#onChangeConnection(boolean)
	 */
	@Override
	public void onChangeConnection(boolean onLine) {
		connected = onLine;
	}
}
