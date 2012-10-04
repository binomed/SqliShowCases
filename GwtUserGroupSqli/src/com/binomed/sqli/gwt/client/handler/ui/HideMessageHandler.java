package com.binomed.sqli.gwt.client.handler.ui;

import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author jfgarreau
 * 
 */
public interface HideMessageHandler extends EventHandler {

	void onHide(CallBackHiddenMessage message);

	void onError(Throwable exception);

}
