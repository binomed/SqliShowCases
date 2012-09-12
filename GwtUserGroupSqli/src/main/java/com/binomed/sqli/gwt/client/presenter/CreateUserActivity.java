package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.presenter.itf.CreateUserPresenter;
import com.binomed.sqli.gwt.client.view.CreateUserView;
import com.binomed.sqli.gwt.shared.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateUserActivity implements Activity, CreateUserPresenter {

	public interface Display {

	}

	private final IClientFactory factory;

	private SqliUserDriver driver;

	public CreateUserActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCancel() {
		// TODO Auto-generated method stub

	}

	public void onStop() {
		// TODO Auto-generated method stub

	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		CreateUserView view = new CreateUserView(this);
		panel.setWidget(view);
		SqliUserEditor editor = new SqliUserEditor(factory);
		view.getUserEditor().add(editor);
		driver = new SqliUserDriver(factory, editor);

		SqliUserProxy user = GWT.create(SqliUserProxy.class);
		user.setFirstName("Jef");
		user.setName("Garreau");

		driver.edit(user);

	}

	public void formSubmit() {
		factory.getService().testService(new AsyncCallback<String>() {

			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				factory.showMessage("Form Submit");

			}

			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				factory.showMessage("Form Submit Error");

			}
		});

	}

}
