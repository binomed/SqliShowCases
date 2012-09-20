package com.binomed.sqli.gwt.client.event.workflow;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.workflow.UserConnectedHandler;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.event.shared.GwtEvent;

public class UserConnectedEvent extends BeanEvent<SqliUserProxy, UserConnectedHandler> {

	public static GwtEvent.Type<UserConnectedHandler> TYPE = new Type<UserConnectedHandler>();

	public UserConnectedEvent(SqliUserProxy bean) {
		super(bean);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserConnectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserConnectedHandler handler) {
		handler.userConnected(getBean());
	}

}
