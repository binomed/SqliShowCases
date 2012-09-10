package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.view.LoginView;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity implements Activity, Presenter {

	public interface Display {

	}

	private final IClientFactory factory;

	public LoginActivity(IClientFactory factory) {
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
		panel.setWidget(new LoginView());

	}

}
