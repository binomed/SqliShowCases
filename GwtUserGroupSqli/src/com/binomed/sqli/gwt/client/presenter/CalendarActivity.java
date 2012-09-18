package com.binomed.sqli.gwt.client.presenter;

import java.util.Date;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.presenter.itf.CalendarPresenter;
import com.binomed.sqli.gwt.client.view.CalendarView;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.api.gwt.client.GoogleApiRequestTransport;
import com.google.api.gwt.client.OAuth2Login;
import com.google.api.gwt.services.calendar.shared.Calendar;
import com.google.api.gwt.services.calendar.shared.Calendar.CalendarAuthScope;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CalendarActivity implements Activity, CalendarPresenter {

	public interface Display extends IsWidget {
		void addEvent(Event event);

		String DATE_TIME_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZZZ";
	}

	private final IClientFactory factory;

	private static final String CLIENT_ID = "1092752234333-altm4129jd9js6o31hgrrfq6qc028k4e.apps.googleusercontent.com";
	private static final String API_KEY = "AIzaSyCZCYiIpkDdgzmXVeJihy3p-lEj33m6WtQ";
	private static final String APPLICATION_NAME = "CalendarSample/1.0";
	private static final Calendar calendar = GWT.create(Calendar.class);
	private Display view;

	public CalendarActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		view = new CalendarView(this);
		panel.setWidget(view);

		calendar.initialize(factory.getEventBus(), //
				new GoogleApiRequestTransport(APPLICATION_NAME, API_KEY));
		login();

	}

	private void login() {
		OAuth2Login.get().authorize(CLIENT_ID, //
				CalendarAuthScope.CALENDAR,//
				new Callback<Void, Exception>() {
					@Override
					public void onSuccess(Void v) {
						getCalendarId();
					}

					@Override
					public void onFailure(Exception e) {
						GWT.log("Auth failed:", e);
					}
				});
	}

	/** Gets the calendar ID of some calendar that the user can write to. */
	private void getCalendarId() {

		String calendarId = "jean.francois.garreau@gmail.com";
		calendar.events().list(calendarId).setMaxResults(10).setTimeMin(DateTimeFormat.getFormat(Display.DATE_TIME_FORMAT).format(new Date())).fire(new Receiver<Events>() {
			// calendar.events().list(calendarId).setMaxResults(10).fire(new Receiver<Events>() {

			@Override
			public void onSuccess(Events response) {
				for (Event event : response.getItems()) {
					view.addEvent(event);
				}

			}

			@Override
			public void onFailure(ServerFailure error) {
				super.onFailure(error);
				GWT.log("Retrieve events failed:" + error.getMessage());
			}

		});

		// // We need to find an ID of a calendar that we have permission to write events to. We'll just
		// // pick the first one that gets returned, and we will delete the event when we're done.
		// calendar.calendarList().list().setMinAccessRole(MinAccessRole.OWNER).fire(new Receiver<CalendarList>() {
		// @Override
		// public void onSuccess(CalendarList list) {
		// String calendarId = list.getItems().get(0).getId();
		//
		// calendar.events().list(calendarId).setMaxResults(10).fire(new Receiver<Events>() {
		//
		// @Override
		// public void onSuccess(Events response) {
		// for (Event event : response.getItems()) {
		//
		// }
		//
		// }
		// });
		// }
		// });
	}

	@Override
	public void eventClick(Appointment event) {
		factory.getPlaceControler().goTo(new EventPlace());
		// factory.showMessage("Event Click : " + event.getTitle());

	}

}
