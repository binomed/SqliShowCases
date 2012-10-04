package com.binomed.sqli.gwt.client.handler.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author jfgarreau
 * 
 */
public interface ConnectionHandler extends EventHandler {

	void onChangeConnection(boolean onLine);

}
