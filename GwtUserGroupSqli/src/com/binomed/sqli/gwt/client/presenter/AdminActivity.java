package com.binomed.sqli.gwt.client.presenter;

import java.util.List;
import java.util.logging.Logger;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.presenter.itf.AdminPresenter;
import com.binomed.sqli.gwt.client.view.AdminView;
import com.binomed.sqli.gwt.shared.SqliUserRequest;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author jfgarreau
 * 
 */
public class AdminActivity implements Activity, AdminPresenter {

	private static final Logger LOGGER = Logger.getLogger("AdminActivity");

	/**
	 * @author jfgarreau
	 * 
	 *         Interface for View
	 */
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

			@Override
			public void onSuccess(Void response) {
				getAllUsers();

			}
		});

	}

	@Override
	public void switchAdminUser(SqliUserProxy user) {
		SqliUserRequest context = factory.getRequestFactory().userRequest();
		SqliUserProxy copyUser = context.edit(user);
		copyUser.setAdmin(!copyUser.isAdmin());
		context.update().using(user).fire(new Receiver<Void>() {

			@Override
			public void onSuccess(Void response) {
				getAllUsers();

			}
		});

	}

}
