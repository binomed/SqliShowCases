package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.Nav;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.HomeActivity.Display //
{

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	@UiField
	Modal modal;
	@UiField
	SimplePanel mainContent;

	@UiField
	Nav userNav;

	private final HomePresenter presenter;

	public HomeView(HomePresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		userNav.setVisible(false);
	}

	/*
	 * Events Part
	 */

	@UiHandler("aPropos")
	public void onAProposClick(ClickEvent event) {
		presenter.eventClick("A Propos");
	}

	@UiHandler("support")
	public void onSupportClick(ClickEvent event) {
		presenter.eventClick("Support");
	}

	@UiHandler("brand")
	public void onBrandClick(ClickEvent event) {
		presenter.brandClick();
	}

	@UiHandler("userEdit")
	public void onUserEdit(ClickEvent event) {
		presenter.editUser();
	}

	@UiHandler("userDisconnect")
	public void onUserDisconnect(ClickEvent event) {
		presenter.disconnectUser();
	}

	@Override
	public void showDialog(String content) {

		showDialog(content, true);
	}

	private void showDialog(String title, boolean closable) {

		boolean previousShow = StringUtils.isNotEmpty(modal.getTitle());
		modal.setTitle(title);
		modal.setCloseVisible(closable);
		if (!previousShow) {
			modal.show();
		}
	}

	@Override
	public AcceptsOneWidget registerMainPanel() {
		return mainContent;

	}

	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	public Widget hasWidget() {
		return this;
	}

	@Override
	public void showLoadMessage(String message) {
		showDialog(message, false);

	}

	@Override
	public void hideLoadMessage() {
		modal.setTitle("");
		modal.hide();

	}

	@Override
	public void showUser(SqliUserProxy user) {
		userNav.setVisible(true);
		userNav.setTitle(user.getFirstName() + " " + user.getName());

	}

	@Override
	public void hideUser() {
		userNav.setVisible(false);
	}

}
