package com.binomed.sqli.gwt.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

/**
 * @author jfgarreau
 * 
 *         RequestFactory Factory for accessing services
 */
public interface SqliRequestFactory extends RequestFactory {

	/**
	 * @return the user service
	 */
	SqliUserRequest userRequest();

}
