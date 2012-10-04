package com.binomed.sqli.gwt.client.presenter;

import java.util.logging.Logger;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.binomed.sqli.gwt.client.view.EventView;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author jfgarreau
 * 
 */
public class EventActivity implements Activity, EventPresenter {

	private static final Logger LOGGER = Logger.getLogger("EventActivity");

	/**
	 * @author jfgarreau
	 * 
	 *         Interface for View
	 */
	public interface Display extends IsWidget {

		void showEvent(Event event);

	}

	private final IClientFactory factory;
	private final EventPlace place;

	private Display view;
	private Event curentEvent;

	public EventActivity(IClientFactory factory, EventPlace place) {
		super();
		this.factory = factory;
		this.place = place;
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
		view = new EventView(this);
		panel.setWidget(view);

		final int eventIndex = factory.getAppStorage().getEventNumber(place.getEventId());
		if (eventIndex != -1) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					EventActivity.this.curentEvent = factory.getAppStorage().getEvent(eventIndex);
					view.showEvent(EventActivity.this.curentEvent);

				}
			});
		} else {

			factory.getEventDetails(place.getEventId(), new Receiver<Event>() {

				@Override
				public void onSuccess(Event response) {
					EventActivity.this.curentEvent = response;
					view.showEvent(response);

				}
			});
		}

	}

	@Override
	public void addToCalendar() {
		factory.addEventToCalendar(curentEvent);
	}

}
