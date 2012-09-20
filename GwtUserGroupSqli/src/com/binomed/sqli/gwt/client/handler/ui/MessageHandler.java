package com.binomed.sqli.gwt.client.handler.ui;

import com.google.gwt.event.shared.EventHandler;

public interface MessageHandler extends EventHandler {

	void onMessage(String message);

	void onError(Throwable exception);

}
