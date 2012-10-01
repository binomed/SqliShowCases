package com.binomed.sqli.gwt.client.driver;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.event.ui.HideMessageEvent;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.shared.SqliUserRequest;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class SqliUserDriver {

	interface Driver extends RequestFactoryEditorDriver<SqliUserProxy, SqliUserEditor> {
	}

	public interface CallBackPersist {

		void persistDone(SqliUserProxy user);
	}

	Driver driver = GWT.create(Driver.class);

	private final IClientFactory clientFactory;
	private final SqliUserEditor editor;
	private final SqliUserRequest context;
	private final SqliUserProxy user;
	private final boolean createMode;

	private boolean firstUser;

	public void setFirstUser(boolean firstUser) {
		this.firstUser = firstUser;
	}

	public SqliUserDriver(IClientFactory clientFactory, SqliUserEditor editor, SqliUserProxy user) {
		super();
		this.clientFactory = clientFactory;
		this.editor = editor;
		this.context = clientFactory.getRequestFactory().userRequest();
		driver.initialize(clientFactory.getRequestFactory(), editor);
		createMode = user == null;
		if (!createMode) {
			this.user = this.context.edit(user);
			// We recopy properties in order to keep a good context
			this.user.setPassword(null);
		} else {
			this.user = context.create(SqliUserProxy.class);

		}
		driver.edit(this.user, context);
	}

	public void save(final CallBackPersist callBack) {
		SqliUserRequest req = (SqliUserRequest) driver.flush();

		clientFactory.getEventBus().fireEvent(new MessageEvent(createMode ? I18N.instance.createLoadMsgCreate() : I18N.instance.createLoadMsgEdit(), false));

		Receiver<Void> callBackFire = new Receiver<Void>() {

			@Override
			public void onSuccess(Void arg0) {
				clientFactory.getEventBus().fireEvent(new HideMessageEvent(new CallBackHiddenMessage() {

					@Override
					public void hidden() {
						callBack.persistDone(user);
					}
				}));
			}

			@Override
			public void onFailure(ServerFailure error) {
				clientFactory.getEventBus().fireEvent(new MessageEvent("Persist failed", true));
				super.onFailure(error);
			}

		};

		if (createMode) {
			if (firstUser) {
				user.setAdmin(true);
			}
			req.persist().using(user).fire(callBackFire);
		} else {
			req.update().using(user).fire(callBackFire);
		}
	}
}
