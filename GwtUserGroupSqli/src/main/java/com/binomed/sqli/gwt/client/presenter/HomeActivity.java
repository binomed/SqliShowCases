package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.view.HomeView;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HomeActivity implements Activity, Presenter {

	public interface Display {

		AcceptsOneWidget registerMainPanel();

	}

	private final IClientFactory factory;

	public HomeActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCancel() {
		// TODO Auto-generated method stub

	}

	public void onStop() {
		// TODO Auto-generated method stub

	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		HomeView view = new HomeView();
		panel.setWidget(view);
		factory.registerMainPanel(view.registerMainPanel());
		factory.getPlaceControler().goTo(new LoginPlace());

	}

}
