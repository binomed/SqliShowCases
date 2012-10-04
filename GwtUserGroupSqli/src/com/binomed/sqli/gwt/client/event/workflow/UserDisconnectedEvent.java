package com.binomed.sqli.gwt.client.event.workflow;

import com.binomed.sqli.gwt.client.handler.workflow.UserDisconnectedHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jfgarreau Send each times the user is disconnect
 */
public class UserDisconnectedEvent extends GwtEvent<UserDisconnectedHandler> {

	public static GwtEvent.Type<UserDisconnectedHandler> TYPE = new Type<UserDisconnectedHandler>();

	public UserDisconnectedEvent() {
		super();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserDisconnectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserDisconnectedHandler handler) {
		handler.userDisconnected();
	}

}
