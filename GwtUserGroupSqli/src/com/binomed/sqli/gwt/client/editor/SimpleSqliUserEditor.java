package com.binomed.sqli.gwt.client.editor;

import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.binomed.sqli.gwt.shared.model.SqliUserLogin;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author jfgarreau Editor for simple user
 */
public class SimpleSqliUserEditor extends Composite implements //
		Editor<SqliUserLogin> //
{

	private static SimpleSqliUserUiBinder uiBinder = GWT.create(SimpleSqliUserUiBinder.class);

	@UiField
	TextBox email;
	@UiField
	PasswordTextBox password;
	@UiField
	Button btnConnexion;

	private final LoginPresenter presenter;

	public SimpleSqliUserEditor(LoginPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	/*
	 * Events Part
	 */

	interface SimpleSqliUserUiBinder extends UiBinder<Widget, SimpleSqliUserEditor> {
	}

	public Widget hasWidget() {
		return this;
	}

	@UiHandler("btnConnexion")
	public void onFormSubmit(ClickEvent event) {
		presenter.formSubmit();
	}

	@UiHandler("linkCreate")
	public void onClickLinkCreate(ClickEvent event) {
		presenter.createUser();
	}

	@UiHandler("password")
	public void onKeyPressPassword(KeyPressEvent event) {
		onKeyPress(event);
	}

	@UiHandler("email")
	public void onKeyPressEmail(KeyPressEvent event) {
		onKeyPress(event);

	}

	private void onKeyPress(KeyPressEvent event) {
		presenter.keyPress(event);
	}

	public void desactivFields() {
		enableFields(false);
	}

	public void activFields() {
		enableFields(true);

	}

	private void enableFields(boolean enabled) {
		email.setEnabled(enabled);
		password.setEnabled(enabled);
		btnConnexion.setEnabled(enabled);
	}

}
