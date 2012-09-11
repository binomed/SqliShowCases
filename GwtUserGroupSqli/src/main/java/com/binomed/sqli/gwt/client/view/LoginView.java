package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.github.gwtbootstrap.client.ui.Form.SubmitEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.LoginActivity.Display //
		, IsWidget//
{

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	private final LoginPresenter presenter;

	public LoginView(LoginPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	/*
	 * Events Part
	 */

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	public Widget hasWidget() {
		return this;
	}

	@UiHandler("formUser")
	public void onFormSubmit(SubmitEvent event) {
		presenter.formSubmit();
	}

	@UiHandler("linkCreate")
	public void onClickLinkCreate(ClickEvent event) {
		presenter.createUser();
	}

}
