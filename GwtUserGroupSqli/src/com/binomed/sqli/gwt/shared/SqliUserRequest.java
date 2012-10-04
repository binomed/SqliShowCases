package com.binomed.sqli.gwt.shared;

import java.util.List;

import com.binomed.sqli.gwt.server.model.SqliUser;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * @author jfgarreau
 * 
 *         RequestFactory service interface
 * 
 */
@Service(SqliUser.class)
public interface SqliUserRequest extends RequestContext {

	/**
	 * @param email
	 * @return the User according to the email. <code>null</code> if not found.
	 */
	Request<SqliUserProxy> findUser(String email);

	/**
	 * @return all the user of data base. An empty list if no users where found.
	 */
	Request<List<SqliUserProxy>> findAllUsers();

	/**
	 * @param id
	 * @return the user corresponding to the id. <code>null</code> if not found.
	 */
	Request<SqliUserProxy> findSqliUser(Long id);

	/**
	 * Check the user according to the email / password
	 * 
	 * @param email
	 * @param password
	 * @return the user if the combination is correct
	 */
	Request<SqliUserProxy> verifyUser(String email, String password);

	/**
	 * Save the user
	 * 
	 * @return
	 */
	InstanceRequest<SqliUserProxy, Void> persist();

	/**
	 * Update the user
	 * 
	 * @return
	 */
	InstanceRequest<SqliUserProxy, Void> update();

	/**
	 * Delete the user
	 * 
	 * @return
	 */
	InstanceRequest<SqliUserProxy, Void> remove();

}
