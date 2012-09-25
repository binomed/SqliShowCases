package com.binomed.sqli.gwt.client;

import java.util.Date;

import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserDisconnectedEvent;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
import com.binomed.sqli.gwt.client.handler.workflow.UserDisconnectedHandler;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.CalendarActivity.Display;
import com.binomed.sqli.gwt.client.presenter.HomeActivity;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.resources.ProjectResources;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.client.storage.ISqliStorage;
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

public class ClientFactory implements IClientFactory //
		, UserDisconnectedHandler //
		, UserConnectedHandler //
{

	// private static final Logger LOGGER = Logger.getLogger("ClientFacotry");

	private final EventBus eventBus = new SimpleEventBus();
	private final SqliServiceAsync rpcService;
	private final PlaceController placeControler = new PlaceController(eventBus);
	private final HasWidgets container;
	private final ActivityManager activityManager;
	private final HomePresenter homePresenter;
	private final SqliRequestFactory requestFactory;
	private final ISqliStorage sqliStorage;

	// private static final String CALENDAR_ID = "jean.francois.garreau@gmail.com";
	private static final String CALENDAR_ID = "ahkgurr9feh3leouhc2na4hbvo@group.calendar.google.com";
	private static final String CLIENT_ID = "1092752234333-altm4129jd9js6o31hgrrfq6qc028k4e.apps.googleusercontent.com";
	private static final String API_KEY = "AIzaSyCZCYiIpkDdgzmXVeJihy3p-lEj33m6WtQ";
	private static final String APPLICATION_NAME = "CalendarSample/1.0";
	private static final Calendar calendar = GWT.create(Calendar.class);

	private SqliUserProxy userConected;
	private Place place;

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
	public SqliRequestFactory getRequestFactory() {
		return requestFactory;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
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
	private void login(Callback<Void, Exception> callBack) {
		OAuth2Login.get().authorize(CLIENT_ID, //
				CalendarAuthScope.CALENDAR,//
				callBack);
	}

	@Override
	public void getEventDetails(String eventId, Receiver<Event> callBack) {
		calendar.events().get(CALENDAR_ID, eventId).fire(callBack);

	}

	@Override
	public SqliUserProxy getConnectedUser() {
		return userConected;
	}

	@Override
	public void userConnected(SqliUserProxy user) {
		this.userConected = user;

	}

	@Override
	public void userDisconnected() {
		this.userConected = null;
	}

	@Override
	public Place getCurrentPlace() {
		return place;
	}

	@Override
	public void updatePlace(Place place) {
		this.place = place;

	}

	@Override
	public void addEventToCalendar(final Event event) {
		login(new Callback<Void, Exception>() {

			@Override
			public void onSuccess(Void result) {
				calendar.calendarList().list().setMinAccessRole(MinAccessRole.WRITER).fire(new Receiver<CalendarList>() {

					@Override
					public void onSuccess(CalendarList response) {
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

			@Override
			public void onFailure(Exception reason) {
				eventBus.fireEvent(new MessageEvent(I18N.instance.calendarMissingAuthorisation(), true));

			}
		});

	}

	@Override
	public ISqliStorage getAppStorage() {
		return this.sqliStorage;
	}
}
