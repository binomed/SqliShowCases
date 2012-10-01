package com.binomed.sqli.gwt.client.view;

import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.event.HiddenEvent;
import com.github.gwtbootstrap.client.ui.event.HiddenHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.HomeActivity.Display //
		, HiddenHandler //
{

	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

	@UiField
	Modal modal;
	@UiField
	SimplePanel mainContent;

	@UiField
	Nav userNav;
	@UiField
	Dropdown userDrop;

	@UiField
	NavLink admin;
	@UiField
	NavLink userEdit;
	@UiField
	NavLink events;
	@UiField
	FlowPanel offLineIcon;

	private final HomePresenter presenter;

	private CallBackHiddenMessage callBackModalHidden;

	public HomeView(HomePresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		userNav.setVisible(false);
		modal.addHiddenHandler(this);
		offLineIcon.setVisible(false);
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

	@UiHandler("admin")
	public void onAdminClick(ClickEvent event) {
		presenter.goToAdmin();
	}

	@UiHandler("events")
	public void onEventsClick(ClickEvent event) {
		presenter.goToEvents();
	}

	@UiHandler("brand")
	public void onBrandClick(ClickEvent event) {
		presenter.brandClick();
	}

	// @UiHandler("logOpenId")
	// public void onOpenIdClick(ClickEvent event) {
	// presenter.openId();
	// }

	@UiHandler("userEdit")
	public void onUserEdit(ClickEvent event) {
		presenter.editUser();
	}

	@UiHandler("userDisconnect")
	public void onUserDisconnect(ClickEvent event) {
		presenter.disconnectUser();
	}

	@Override
	public void showDialog(String title, boolean closable) {
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
	public void hideLoadMessage(CallBackHiddenMessage callBack) {
		this.callBackModalHidden = callBack;
		modal.setTitle("");
		modal.hide();

	}

	@Override
	public void showUser(SqliUserProxy user, boolean onLine) {
		userNav.setVisible(true);
		String text = user.getFirstName() + " " + user.getName();
		userDrop.setText(text);
		userDrop.setTitle(text);
		userNav.setTitle(text);
		userEdit.setDisabled(!onLine);

	}

	@Override
	public void hideUser() {
		userNav.setVisible(false);
	}

	@Override
	public void onHidden(HiddenEvent hiddenEvent) {
		if (this.callBackModalHidden != null) {
			callBackModalHidden.hidden();
		}
		this.callBackModalHidden = null;
	}

	@Override
	public void showAdmin() {
		admin.setVisible(true);
	}

	@Override
	public void hideAdmin() {
		admin.setVisible(false);
	}

	@Override
	public void showEvents() {
		events.setVisible(true);

	}

	@Override
	public void hideEvents() {
		events.setVisible(false);

	}

	@Override
	public void showOffLineIcon(boolean onLine) {
		offLineIcon.setVisible(!onLine);

	}

}
