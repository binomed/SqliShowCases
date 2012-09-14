package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.presenter.itf.CreateUserPresenter;
import com.binomed.sqli.gwt.client.view.CreateUserView;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class CreateUserActivity implements Activity, CreateUserPresenter {

	public interface Display extends IsWidget {

		FlowPanel getUserEditor();

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
		Display view = new CreateUserView(this);
		panel.setWidget(view);
		// We define the editor and map it
		SqliUserEditor editor = new SqliUserEditor(factory, this);
		view.getUserEditor().add(editor);
		driver = new SqliUserDriver(factory, editor);

	}

	public void formSubmit() {
		driver.save();
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
