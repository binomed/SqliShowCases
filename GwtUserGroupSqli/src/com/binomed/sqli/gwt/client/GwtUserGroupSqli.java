package com.binomed.sqli.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtUserGroupSqli implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	@Override
	public void onModuleLoad() {
		// Load and initialize the window
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading"));
		GWT.create(IClientFactory.class);

		// This is where the magic happens - This code is only usefull when the OpenID provider
		// redirects the user back to your site - please visit openid.net for valid parameters.
		// The "if" statement checks to make sure that it is a valid response from the OpenID
		// provider - You can do anything you want with the results here such as verifying the
		// response with the server-side code
		if (Window.Location.getParameter("openid.rpnonce") != null) {
			String s = Window.Location.getParameter("openid.mode");
			// executes this if the user cancels the authentication process and the OpenID
			// providers returns to the your site
			if (s.equals("cancel")) {
				Window.alert("Server returned openid.mode=cancel <br>You need to Accept not Cancel");
			}
			// Here, I am assuming that everything is fine and that the user has been sucessfully logged in
			else {
				Window.alert("You have successfully signed in");
				// vp.setVisible(false);
			}

		}

	}
}
