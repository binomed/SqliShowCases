package com.binomed.sqli.gwt.client.driver;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.shared.SqliUserRequest;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class SqliUserDriver {

	interface Driver extends RequestFactoryEditorDriver<SqliUserProxy, SqliUserEditor> {
	}

	Driver driver = GWT.create(Driver.class);

	private final IClientFactory clientFactory;
	private final SqliUserEditor editor;
	private final SqliUserRequest context;
	private final SqliUserProxy user;

	public SqliUserDriver(IClientFactory clientFactory, SqliUserEditor editor) {
		super();
		this.clientFactory = clientFactory;
		this.editor = editor;
		this.context = clientFactory.getRequestFactory().userRequest();
		driver.initialize(clientFactory.getRequestFactory(), editor);
		user = context.create(SqliUserProxy.class);
		driver.edit(user, context);
	}

	public void save() {
		SqliUserRequest req = (SqliUserRequest) driver.flush();

		// SqliUserProxy edited = driver.flush();
		req.persist().using(user).fire(new Receiver<Void>() {

			@Override
			public void onSuccess(Void arg0) {
				clientFactory.getEventBus().fireEvent(new MessageEvent("Persist ok"));

			}

			@Override
			public void onFailure(ServerFailure error) {
				clientFactory.getEventBus().fireEvent(new MessageEvent("Persist failed"));
				// TODO Auto-generated method stub
				super.onFailure(error);
			}

		});
		// Window.alert(" first name: " + edited.getFirstName() + ", last name: " + edited.getName());
	}
}
