package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.presenter.itf.CalendarPresenter;
import com.binomed.sqli.gwt.client.view.CalendarView;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CalendarActivity implements Activity, CalendarPresenter {

	public interface Display extends IsWidget {
		void addEvent(Event event);

		String DATE_TIME_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZZZ";
		String DATE_TIME_FORMAT_DAY = "yyyy-MM-dd";

		void showLoad();

		void hideLoad();
	}

	private final IClientFactory factory;

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

		view.showLoad();
		getCalendarId();
		// login();

	}

	// private void login() {
	// OAuth2Login.get().authorize(CLIENT_ID, //
	// CalendarAuthScope.CALENDAR,//
	// new Callback<Void, Exception>() {
	// @Override
	// public void onSuccess(Void v) {
	// getCalendarId();
	// }
	//
	// @Override
	// public void onFailure(Exception e) {
	// GWT.log("Auth failed:", e);
	// }
	// });
	// }

	/** Gets the calendar ID of some calendar that the user can write to. */
	private void getCalendarId() {

		factory.getListEvents(0, 10, new Receiver<Events>() {
			// calendar.events().list(calendarId).setMaxResults(10).fire(new Receiver<Events>() {

			@Override
			public void onSuccess(Events response) {
				for (Event event : response.getItems()) {
					view.addEvent(event);
				}
				view.hideLoad();

			}

			@Override
			public void onFailure(ServerFailure error) {
				super.onFailure(error);
				factory.getEventBus().fireEvent(new MessageEvent("Erreur retrieve calendar events : " + error.getMessage()));
				GWT.log("Retrieve events failed:" + error.getMessage());
				view.hideLoad();
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
		factory.getPlaceControler().goTo(new EventPlace(event.getId()));

	}

}
