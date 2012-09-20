package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.binomed.sqli.gwt.client.view.EventView;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class EventActivity implements Activity, EventPresenter {

	public interface Display extends IsWidget {

		void showEvent(Event event);

	}

	private final IClientFactory factory;
	private final EventPlace place;

	private Display view;

	public EventActivity(IClientFactory factory, EventPlace place) {
		super();
		this.factory = factory;
		this.place = place;
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

		view = new EventView(this);
		panel.setWidget(view);

		factory.getEventDetails(place.getEventId(), new Receiver<Event>() {

			@Override
			public void onSuccess(Event response) {
				view.showEvent(response);

			}
		});

	}

}
