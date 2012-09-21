package com.binomed.sqli.gwt.client.event.ui;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.ui.MessageHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MessageEvent extends BeanEvent<String, MessageHandler> {

	public static GwtEvent.Type<MessageHandler> TYPE = new Type<MessageHandler>();

	private final boolean showClose;

	public MessageEvent(String bean, boolean showClose) {
		super(bean);
		this.showClose = showClose;
	}

	public MessageEvent(Throwable exception) {
		super(exception);
		this.showClose = true;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MessageHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MessageHandler handler) {
		if (getException() != null) {
			handler.onError(getException());
		} else {
			handler.onMessage(getBean(), showClose);
		}

	}

}
