package com.binomed.sqli.gwt.client.event.ui;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.ui.ConnectionHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ConnectionEvent extends BeanEvent<Boolean, ConnectionHandler> {

	public static GwtEvent.Type<ConnectionHandler> TYPE = new Type<ConnectionHandler>();

	public ConnectionEvent(Boolean bean) {
		super(bean);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ConnectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ConnectionHandler handler) {
		handler.onChangeConnection(getBean());
	}

}
