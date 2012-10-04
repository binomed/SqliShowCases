package com.binomed.sqli.gwt.client.event.ui;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.ui.HideMessageHandler;
import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jfgarreau Event for hiding message
 */
public class HideMessageEvent extends BeanEvent<CallBackHiddenMessage, HideMessageHandler> {

	public static GwtEvent.Type<HideMessageHandler> TYPE = new Type<HideMessageHandler>();

	public HideMessageEvent(CallBackHiddenMessage bean) {
		super(bean);
	}

	public HideMessageEvent(Throwable exception) {
		super(exception);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HideMessageHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideMessageHandler handler) {
		if (getException() != null) {
			handler.onError(getException());
		} else {
			handler.onHide(getBean());
		}

	}

}
