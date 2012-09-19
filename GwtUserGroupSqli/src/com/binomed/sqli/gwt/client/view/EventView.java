package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EventView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.EventActivity.Display //
{

	private static EventViewUiBinder uiBinder = GWT.create(EventViewUiBinder.class);

	private final EventPresenter presenter;

	@UiField
	Heading eventName;

	@UiField
	Paragraph eventDetails;
	@UiField
	ControlLabel eventDate, eventHour, eventRoom;

	public EventView(EventPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;

	}

	interface EventViewUiBinder extends UiBinder<Widget, EventView> {
	}

	public Widget hasWidget() {
		return this;
	}

	@Override
	public void showEvent(Event event) {
		eventName.setText(event.getSummary());
		eventDetails.setText(event.getDescription());
		eventRoom.setTitle(event.getLocation());

	}

}
