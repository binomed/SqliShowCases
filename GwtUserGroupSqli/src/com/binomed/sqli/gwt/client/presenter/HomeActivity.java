package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.event.ui.HideMessageEvent;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserDisconnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserUpdateEvent;
import com.binomed.sqli.gwt.client.handler.ui.HideMessageHandler;
import com.binomed.sqli.gwt.client.handler.ui.MessageHandler;
import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
import com.binomed.sqli.gwt.client.handler.workflow.UserUpdateHandler;
import com.binomed.sqli.gwt.client.place.AdminPlace;
import com.binomed.sqli.gwt.client.place.CalendarPlace;
import com.binomed.sqli.gwt.client.place.EditUserPlace;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.view.HomeView;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public class HomeActivity implements HomePresenter //
		, MessageHandler //
		, UserConnectedHandler //
		, UserUpdateHandler //
		, HideMessageHandler //
{

	public interface Display extends IsWidget {

		AcceptsOneWidget registerMainPanel();

		void showDialog(String title, boolean closable);

		void hideLoadMessage(CallBackHiddenMessage callBack);

		void showUser(SqliUserProxy user);

		void hideUser();

		void showAdmin();

		void hideAdmin();

		void showEvents();

		void hideEvents();

	}

	private final IClientFactory factory;

	private final Display view;

	public HomeActivity(IClientFactory factory, HasWidgets panel) {
		super();
		this.factory = factory;
		view = new HomeView(this);
		panel.add((HomeView) view);
		factory.getEventBus().addHandler(MessageEvent.TYPE, this);
		factory.getEventBus().addHandler(UserConnectedEvent.TYPE, this);
		factory.getEventBus().addHandler(UserUpdateEvent.TYPE, this);
		factory.getEventBus().addHandler(HideMessageEvent.TYPE, this);

		view.hideAdmin();
		view.hideEvents();
	}

	@Override
	public AcceptsOneWidget getMainPanel() {
		return view.registerMainPanel();
	}

	@Override
	public void brandClick() {
		factory.getPlaceControler().goTo(new LoginPlace());

	}

	@Override
	public void onMessage(String message, boolean showClose) {
		view.showDialog(message, showClose);

	}

	@Override
	public void onError(Throwable exception) {
		view.showDialog(exception.getMessage(), true);

	}

	@Override
	public void eventClick(String source) {
		view.showDialog(source, true);

	}

	@Override
	public void userConnected(SqliUserProxy user) {
		view.showUser(user);
		view.showEvents();
		if (user.isAdmin()) {
			view.showAdmin();
		}
	}

	@Override
	public void editUser() {
		factory.getPlaceControler().goTo(new EditUserPlace(factory.getConnectedUser()));
	}

	@Override
	public void disconnectUser() {
		view.hideUser();
		view.hideAdmin();
		view.hideEvents();
		factory.getEventBus().fireEvent(new UserDisconnectedEvent());

	}

	@Override
	public void userUpdate(SqliUserProxy user) {
		view.showUser(user);

	}

	@Override
	public void onHide(CallBackHiddenMessage message) {
		view.hideLoadMessage(message);
	}

	@Override
	public void goToAdmin() {
		factory.getPlaceControler().goTo(new AdminPlace());

	}

	@Override
	public void goToEvents() {
		factory.getPlaceControler().goTo(new CalendarPlace());

	}

	// @Override
	// public void openId() {
	// factory.getService().getOpenIdProtocols(new AsyncCallback<List<OpenIdProtocls>>() {
	//
	// @Override
	// public void onSuccess(List<OpenIdProtocls> result) {
	// // TODO Auto-generated method stub
	// Modal modal = new Modal();
	// FlowPanel panel = new FlowPanel();
	// for (OpenIdProtocls res : result) {
	// final NavLink link = new NavLink(res.getName(), res.getUrl());
	// link.addClickHandler(new ClickHandler() {
	//
	// @Override
	// public void onClick(ClickEvent event) {
	// factory.getService().authenticateOpenId(link.getAnchor().getHref(), new AsyncCallback<UserOpenId>() {
	//
	// @Override
	// public void onSuccess(UserOpenId result) {
	// Window.alert("Log ! " + result.getParams().get("login") + " : " + result.getParams().get("name"));
	// }
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// GWT.getUncaughtExceptionHandler().onUncaughtException(caught);
	// Window.alert("Error : " + caught.getLocalizedMessage());
	// }
	// });
	//
	// }
	// });
	// panel.add(link);
	// }
	// modal.add(panel);
	// modal.show();
	// }
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// GWT.getUncaughtExceptionHandler().onUncaughtException(caught);
	// Window.alert("Error : " + caught.getLocalizedMessage());
	//
	// }
	// });
	//
	// }

}
