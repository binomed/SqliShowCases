package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.CreateUserPlace;
import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.binomed.sqli.gwt.client.view.LoginView;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity implements Activity, LoginPresenter {

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
		panel.setWidget(new LoginView(this));

	}

	public void formSubmit() {
		factory.getService().testService(new AsyncCallback<String>() {

			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				factory.showMessage("Form Submit");

			}

			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				factory.showMessage("Form Submit Error");

			}
		});

	}

	public void createUser() {
		factory.getPlaceControler().goTo(new CreateUserPlace());

	}

}
