package com.binomed.sqli.gwt.client.handler.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author jfgarreau
 * 
 */
public interface MessageHandler extends EventHandler {

	void onMessage(String message, boolean showClose);

	void onError(Throwable exception);

}
