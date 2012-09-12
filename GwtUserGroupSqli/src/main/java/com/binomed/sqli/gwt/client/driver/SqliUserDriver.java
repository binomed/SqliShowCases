package com.binomed.sqli.gwt.client.driver;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.shared.SqliUserProxy;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;

public class SqliUserDriver {

	interface Driver extends SimpleBeanEditorDriver<SqliUserProxy, SqliUserEditor> {
	}

	Driver driver = GWT.create(Driver.class);

	private final IClientFactory clientFactory;
	private final SqliUserEditor editor;

	public SqliUserDriver(IClientFactory clientFactory, SqliUserEditor editor) {
		super();
		this.clientFactory = clientFactory;
		this.editor = editor;
	}

	public void edit(SqliUserProxy user) {
		// SqliUserEditor editor = new SqliUserEditor(clientFactory);
		driver.initialize(editor);
		driver.edit(user);
	}

	public void save() {
		SqliUserProxy edited = driver.flush();
		Window.alert(" first name: " + edited.getFirstName() + ", last name: " + edited.getName());
	}
}
