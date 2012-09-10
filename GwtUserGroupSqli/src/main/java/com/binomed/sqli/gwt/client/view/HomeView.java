package com.binomed.sqli.gwt.client.view;

import com.github.gwtbootstrap.client.ui.Modal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.HomeActivity.Display //
		, IsWidget//
{

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	@UiField
	Modal modal;
	@UiField
	SimplePanel mainContent;

	public HomeView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	/*
	 * Events Part
	 */

	@UiHandler("aPropos")
	public void onAProposClick(ClickEvent event) {
		showDialog("A Propos");
	}

	@UiHandler("support")
	public void onSupportClick(ClickEvent event) {
		showDialog("Support");
	}

	private void showDialog(String content) {

		modal.setTitle(content);
		modal.show();

	}

	public AcceptsOneWidget registerMainPanel() {
		return mainContent;

	}

	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	public Widget hasWidget() {
		return this;
	}

}
