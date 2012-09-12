package com.binomed.sqli.gwt.client.editor;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.shared.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Form.SubmitEvent;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
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
	TextBox password;
	@UiField
	CheckBox contactAllowed;

	private final IClientFactory clientFacotry;

	public SqliUserEditor(IClientFactory clientFactory) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFacotry = clientFactory;
	}

	/*
	 * Events Part
	 */

	interface SqliUserUiBinder extends UiBinder<Widget, SqliUserEditor> {
	}

	public Widget hasWidget() {
		return this;
	}

	@UiHandler("formUser")
	public void onFormSubmit(SubmitEvent event) {
	}

}
