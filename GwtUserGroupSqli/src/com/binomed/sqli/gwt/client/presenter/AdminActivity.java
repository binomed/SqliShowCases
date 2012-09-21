package com.binomed.sqli.gwt.client.presenter;

import java.util.List;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.presenter.itf.AdminPresenter;
import com.binomed.sqli.gwt.client.view.AdminView;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class AdminActivity implements Activity, AdminPresenter {

	public interface Display extends IsWidget {

		void fillUsers(List<SqliUserProxy> userList);
	}

	private final IClientFactory factory;

	private Display view;

	public AdminActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		view = new AdminView(this);
		panel.setWidget(view);

		getAllUsers();

	}

	private void getAllUsers() {
		factory.getRequestFactory().userRequest().findAllUsers().fire(new Receiver<List<SqliUserProxy>>() {

			@Override
			public void onSuccess(List<SqliUserProxy> response) {
				view.fillUsers(response);

			}
		});
	}

	@Override
	public void deleteUser(SqliUserProxy user) {
		factory.getRequestFactory().userRequest().remove().using(user).fire(new Receiver<Void>() {
			// factory.getRequestFactoryContext().remove().using(user).fire(new Receiver<Void>() {

			@Override
			public void onSuccess(Void response) {
				getAllUsers();

			}
		});

	}

}
