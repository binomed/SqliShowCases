package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.CreateUserPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class CreateUserView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.CreateUserActivity.Display //
		, IsWidget//
{

	private static CreateUserViewUiBinder uiBinder = GWT.create(CreateUserViewUiBinder.class);

	private final CreateUserPresenter presenter;

	@UiField
	FlowPanel userEditor;

	public CreateUserView(CreateUserPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	/*
	 * Events Part
	 */

	public FlowPanel getUserEditor() {
		return userEditor;
	}

	interface CreateUserViewUiBinder extends UiBinder<Widget, CreateUserView> {
	}

	public Widget hasWidget() {
		return this;
	}

	// @UiHandler("formUser")
	// public void onFormSubmit(SubmitEvent event) {
	// presenter.formSubmit();
	// }

}
