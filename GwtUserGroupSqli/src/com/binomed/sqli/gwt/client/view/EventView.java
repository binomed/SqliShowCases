package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EventView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.EventActivity.Display //
{

	private static EventViewUiBinder uiBinder = GWT.create(EventViewUiBinder.class);

	private final EventPresenter presenter;

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

}
