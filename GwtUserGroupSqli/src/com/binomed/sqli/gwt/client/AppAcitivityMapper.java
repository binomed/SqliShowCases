package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.place.AdminPlace;
import com.binomed.sqli.gwt.client.place.CalendarPlace;
import com.binomed.sqli.gwt.client.place.CreateUserPlace;
import com.binomed.sqli.gwt.client.place.EditUserPlace;
import com.binomed.sqli.gwt.client.place.EventPlace;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.AdminActivity;
import com.binomed.sqli.gwt.client.presenter.CalendarActivity;
import com.binomed.sqli.gwt.client.presenter.CreateUserActivity;
import com.binomed.sqli.gwt.client.presenter.EditUserActivity;
import com.binomed.sqli.gwt.client.presenter.EventActivity;
import com.binomed.sqli.gwt.client.presenter.LoginActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppAcitivityMapper implements ActivityMapper {

	private final IClientFactory clientFactory;

	public AppAcitivityMapper(IClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {

		if (!(place instanceof EditUserPlace) //
				&& !(place instanceof CreateUserPlace) //
		) {
			clientFactory.updatePlace(place);
		}

		if (place instanceof LoginPlace) {
			return new LoginActivity(clientFactory);
		}
		if (place instanceof CreateUserPlace) {
			return new CreateUserActivity(clientFactory);
		}
		if (place instanceof CalendarPlace) {
			return new CalendarActivity(clientFactory);
		}
		if (place instanceof EventPlace) {
			return new EventActivity(clientFactory, (EventPlace) place);
		}
		if (place instanceof EditUserPlace) {
			return new EditUserActivity(clientFactory, (EditUserPlace) place);
		}
		if (place instanceof AdminPlace) {
			return new AdminActivity(clientFactory);
		}
		return null;
	}
}
