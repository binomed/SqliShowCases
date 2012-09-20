package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserDisconnectedEvent;
import com.binomed.sqli.gwt.client.handler.ui.MessageHandler;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
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
{

	public interface Display extends IsWidget {

		AcceptsOneWidget registerMainPanel();

		void showDialog(String source);

		void showLoadMessage(String message);

		void hideLoadMessage();

		void showUser(SqliUserProxy user);

		void hideUser();

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
	public void onMessage(String message) {
		view.showDialog(message);

	}

	@Override
	public void onError(Throwable exception) {
		view.showDialog(exception.getMessage());

	}

	@Override
	public void eventClick(String source) {
		view.showDialog(source);

	}

	@Override
	public void userConnected(SqliUserProxy user) {
		view.showUser(user);
	}

	@Override
	public void editUser() {
		factory.getPlaceControler().goTo(new EditUserPlace(factory.getConnectedUser()));
	}

	@Override
	public void disconnectUser() {
		view.hideUser();
		factory.getEventBus().fireEvent(new UserDisconnectedEvent());
	}

}
