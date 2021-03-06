package com.binomed.sqli.gwt.client.event.workflow;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jfgarreau Send each times the user is connect
 */
public class UserConnectedEvent extends BeanEvent<SqliUserProxy, UserConnectedHandler> {

	public static GwtEvent.Type<UserConnectedHandler> TYPE = new Type<UserConnectedHandler>();

	private final boolean onLine;

	public UserConnectedEvent(SqliUserProxy bean, boolean onLine) {
		super(bean);
		this.onLine = onLine;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserConnectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserConnectedHandler handler) {
		handler.userConnected(getBean(), onLine);
	}

}
