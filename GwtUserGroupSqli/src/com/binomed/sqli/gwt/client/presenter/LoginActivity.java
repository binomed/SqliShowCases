package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SimpleSqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SimpleSqliUserEditor;
import com.binomed.sqli.gwt.client.place.CalendarPlace;
import com.binomed.sqli.gwt.client.place.CreateUserPlace;
import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.binomed.sqli.gwt.client.view.LoginView;
import com.binomed.sqli.gwt.shared.model.SqliUserLogin;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class LoginActivity implements Activity, LoginPresenter {

	public interface Display extends IsWidget {

		FlowPanel getUserEditor();
	}

	private final IClientFactory factory;

	private SimpleSqliUserDriver driver;

	public LoginActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		panel.setWidget(new LoginView(this));

		Display view = new LoginView(this);
		panel.setWidget(view);
		// We define the editor and map it
		SimpleSqliUserEditor editor = new SimpleSqliUserEditor(factory, this);
		view.getUserEditor().add(editor);
		driver = new SimpleSqliUserDriver(factory, editor);

	}

	@Override
	public void formSubmit() {
		SqliUserLogin user = driver.retrieveUser();

		Request<SqliUserProxy> request = factory.getRequestFactory().userRequest().verifyUser(user.getEmail(), user.getPassword());
		request.fire(new Receiver<SqliUserProxy>() {

			@Override
			public void onSuccess(SqliUserProxy user) {
				if (user == null) {
					// factory.showMessage("Unknow User");

				} else {
					factory.showMessage("User found");

				}

			}

			@Override
			public void onFailure(ServerFailure error) {
				factory.showMessage("Error Verify User: " + error.getMessage());
				super.onFailure(error);
			}
		});

		// TODO à déplacer
		factory.getPlaceControler().goTo(new CalendarPlace());

	}

	@Override
	public void createUser() {
		factory.getPlaceControler().goTo(new CreateUserPlace());

	}

}
