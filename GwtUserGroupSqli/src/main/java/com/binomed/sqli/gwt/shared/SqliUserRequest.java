package com.binomed.sqli.gwt.shared;

import com.binomed.sqli.gwt.server.model.SqliUser;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(SqliUser.class)
public interface SqliUserRequest extends RequestContext {

	Request<SqliUserProxy> findUser(String email);

	Request<SqliUserProxy> findUserById(Long id);

	InstanceRequest<SqliUserProxy, Void> persist();

}
