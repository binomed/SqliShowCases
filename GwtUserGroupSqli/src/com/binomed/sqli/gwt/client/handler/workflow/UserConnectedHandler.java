package com.binomed.sqli.gwt.client.handler.workflow;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author jfgarreau
 * 
 */
public interface UserConnectedHandler extends EventHandler {

	void userConnected(SqliUserProxy user, boolean onLine);

}
