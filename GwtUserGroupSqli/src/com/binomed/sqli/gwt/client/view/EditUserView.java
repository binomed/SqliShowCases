package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.EditUserPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author jfgarreau
 * 
 */
public class EditUserView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.EditUserActivity.Display //
{

	private static EditUserViewUiBinder uiBinder = GWT.create(EditUserViewUiBinder.class);

	private final EditUserPresenter presenter;

	@UiField
	FlowPanel userEditor;

	public EditUserView(EditUserPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	/*
	 * Events Part
	 */

	@Override
	public FlowPanel getUserEditor() {
		return userEditor;
	}

	interface EditUserViewUiBinder extends UiBinder<Widget, EditUserView> {
	}

	public Widget hasWidget() {
		return this;
	}

}
