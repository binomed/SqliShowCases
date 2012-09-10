package com.binomed.sqli.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.LoginActivity.Display //
		, IsWidget//
{

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	public LoginView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	/*
	 * Events Part
	 */

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	public Widget hasWidget() {
		return this;
	}

}
