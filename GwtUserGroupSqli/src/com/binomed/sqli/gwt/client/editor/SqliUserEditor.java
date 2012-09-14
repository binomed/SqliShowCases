package com.binomed.sqli.gwt.client.editor;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.presenter.itf.CreateUserPresenter;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SqliUserEditor extends Composite implements //
		Editor<SqliUserProxy> //
{

	private static SqliUserUiBinder uiBinder = GWT.create(SqliUserUiBinder.class);

	@UiField
	TextBox firstName;
	@UiField
	TextBox name;
	@UiField
	TextBox email;
	@UiField
	PasswordTextBox password;
	@UiField
	CheckBox contactAllowed;

	private final IClientFactory clientFacotry;
	private final CreateUserPresenter presenter;

	public SqliUserEditor(IClientFactory clientFactory, CreateUserPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFacotry = clientFactory;
		this.presenter = presenter;
	}

	/*
	 * Events Part
	 */

	interface SqliUserUiBinder extends UiBinder<Widget, SqliUserEditor> {
	}

	public Widget hasWidget() {
		return this;
	}

	@UiHandler("btnSave")
	public void onFormSubmit(ClickEvent event) {
		presenter.formSubmit();
	}

}
