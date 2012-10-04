package com.binomed.sqli.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side service
 */
@RemoteServiceRelativePath("sqli")
public interface SqliService extends RemoteService {

	/**
	 * Method to test the connectivity
	 * 
	 * @return "OK" if the ping works well.
	 */
	String testConnexionService();

}
