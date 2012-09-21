package com.binomed.sqli.gwt.client.handler.workflow;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.event.shared.EventHandler;

public interface UserUpdateHandler extends EventHandler {

	void userUpdate(SqliUserProxy user);

}
