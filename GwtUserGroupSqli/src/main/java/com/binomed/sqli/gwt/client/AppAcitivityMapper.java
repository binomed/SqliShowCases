package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.place.LoginPlace;
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

	public Activity getActivity(Place place) {
		if (place instanceof LoginPlace) {
			return new LoginActivity(clientFactory);
		}
		return null;
	}
}
