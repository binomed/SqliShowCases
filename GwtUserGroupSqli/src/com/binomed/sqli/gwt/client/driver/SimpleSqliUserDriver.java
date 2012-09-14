package com.binomed.sqli.gwt.client.driver;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.editor.SimpleSqliUserEditor;
import com.binomed.sqli.gwt.shared.SqliUserRequest;
import com.binomed.sqli.gwt.shared.model.SqliUserLogin;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;

public class SimpleSqliUserDriver {

	interface Driver extends SimpleBeanEditorDriver<SqliUserLogin, SimpleSqliUserEditor> {
	}

	Driver driver = GWT.create(Driver.class);

	private final IClientFactory clientFactory;
	private final SimpleSqliUserEditor editor;
	private final SqliUserRequest context;
	private final SqliUserLogin user;

	public SimpleSqliUserDriver(IClientFactory clientFactory, SimpleSqliUserEditor editor) {
		super();
		this.clientFactory = clientFactory;
		this.editor = editor;
		this.context = clientFactory.getRequestFactory().userRequest();
		driver.initialize(editor);
		user = new SqliUserLogin();
		driver.edit(user);
	}

	public SqliUserLogin retrieveUser() {
		return driver.flush();
	}

}
