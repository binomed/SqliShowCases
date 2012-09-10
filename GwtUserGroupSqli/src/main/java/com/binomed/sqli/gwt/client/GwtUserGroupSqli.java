package com.binomed.sqli.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtUserGroupSqli implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	public void onModuleLoad() {
		// Load and initialize the window
		GWT.create(IClientFactory.class);
	}
}
