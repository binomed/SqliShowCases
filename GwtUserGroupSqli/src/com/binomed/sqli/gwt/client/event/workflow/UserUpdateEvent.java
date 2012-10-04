package com.binomed.sqli.gwt.client.event.workflow;

import com.binomed.sqli.gwt.client.event.BeanEvent;
import com.binomed.sqli.gwt.client.handler.workflow.UserUpdateHandler;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jfgarreau
 * 
 *         Send each times a user is update
 */
public class UserUpdateEvent extends BeanEvent<SqliUserProxy, UserUpdateHandler> {

	public static GwtEvent.Type<UserUpdateHandler> TYPE = new Type<UserUpdateHandler>();

	public UserUpdateEvent(SqliUserProxy user) {
		super(user);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserUpdateHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserUpdateHandler handler) {
		handler.userUpdate(getBean());
	}

}
