package com.binomed.sqli.gwt.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.presenter.itf.CalendarPresenter;
import com.binomed.sqli.gwt.client.view.CalendarView;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.Events;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * @author jfgarreau
 * 
 */
public class CalendarActivity implements Activity, CalendarPresenter {

	private static final Logger LOGGER = Logger.getLogger("CalendarActivity");

	/**
	 * @author jfgarreau
	 * 
	 *         Interface for View
	 */
	public interface Display extends IsWidget {
		void addEvent(Event event);

		String DATE_TIME_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZZZ";
		String DATE_TIME_FORMAT_DAY = "yyyy-MM-dd";

		void showLoad();

		void hideLoad();

		void initCalendar();
	}

	private final IClientFactory factory;

	private Display view;

	public CalendarActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		view = new CalendarView(this);
		panel.setWidget(view);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				view.initCalendar();

			}
		});
		view.showLoad();
		getCalendarId();

	}

	/** Gets the calendar ID of some calendar that the user can write to. */
	private void getCalendarId() {
		if (!factory.isConnect() && factory.getAppStorage().getNbEvents() > 0) {

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					List<Event> eventList = factory.getAppStorage().getListEvents();
					for (Event event : eventList) {
						view.addEvent(event);
					}
					view.hideLoad();
				}
			});

		} else {
			factory.getListEvents(0, 10, new Receiver<Events>() {

				@Override
				public void onSuccess(Events response) {
					List<Event> eventList = new ArrayList<Event>();
					for (Event event : response.getItems()) {
						view.addEvent(event);
						eventList.add(event);
					}
					view.hideLoad();

					if (factory.isConnect()) {
						factory.getAppStorage().clearEvents();
					}
					factory.getAppStorage().saveListEvents(eventList);

				}

				@Override
				public void onFailure(ServerFailure error) {
					super.onFailure(error);
					factory.getEventBus().fireEvent(new MessageEvent("Erreur retrieve calendar events : " + error.getMessage(), true));
					view.hideLoad();
				}

			});
		}

	}

	@Override
	public void eventClick(Appointment event) {
		factory.getPlaceControler().goTo(new EventPlace(event.getId()));
	}

}
