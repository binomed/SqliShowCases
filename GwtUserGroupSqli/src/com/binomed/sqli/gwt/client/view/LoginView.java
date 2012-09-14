package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.LoginActivity.Display //
		, IsWidget//
		, Editor<SqliUserProxy> //
{

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	private final LoginPresenter presenter;

	@UiField
	FlowPanel userEditor;

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

	public FlowPanel getUserEditor() {
		return userEditor;
	}

}
