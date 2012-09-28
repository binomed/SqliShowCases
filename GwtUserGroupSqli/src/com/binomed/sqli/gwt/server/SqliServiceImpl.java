package com.binomed.sqli.gwt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.binomed.sqli.gwt.client.SqliService;
import com.binomed.sqli.gwt.shared.model.OpenIdProtocls;
import com.binomed.sqli.gwt.shared.model.UserOpenId;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SqliServiceImpl extends RemoteServiceServlet implements SqliService {

	private static final Map<String, String> openIdProviders;
	static {
		openIdProviders = new HashMap<String, String>();
		openIdProviders.put("Google", "google.com/accounts/o8/id");
		openIdProviders.put("Yahoo", "yahoo.com");
		openIdProviders.put("MySpace", "myspace.com");
		openIdProviders.put("AOL", "aol.com");
		openIdProviders.put("MyOpenId.com", "myopenid.com");
	}

	@Override
	public String testConnexionService() {
		return "OK";
	}

	@Override
	public UserOpenId authenticateOpenId(String provider_url) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // or req.getUserPrincipal()
		// Set<String> attributes = new HashSet<String>();
		//
		// resp.setContentType("text/html");
		// PrintWriter out = resp.getWriter();

		// if (user != null) {
		UserOpenId userOpenId = new UserOpenId();
		userOpenId.setParams(new HashMap<String, String>());
		userOpenId.getParams().put("login", user.getEmail());
		userOpenId.getParams().put("name", user.getNickname());
		return userOpenId;
		// out.println("Hello <i>" + user.getNickname() + "</i>!");
		// out.println("[<a href=\"" + userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>]");
		// } else {
		// out.println("Hello world! Sign in at: ");
		// for (String providerName : openIdProviders.keySet()) {
		// String providerUrl = openIdProviders.get(providerName);
		// String loginUrl = userService.createLoginURL(req.getRequestURI(), null, providerUrl, attributes);
		// out.println("[<a href=\"" + loginUrl + "\">" + providerName + "</a>] ");
		// }
		// }
	}

	@Override
	public List<OpenIdProtocls> getOpenIdProtocols() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // or req.getUserPrincipal()
		List<OpenIdProtocls> openIdProtols = new ArrayList<OpenIdProtocls>();
		Set<String> attributes = new HashSet<String>();
		//
		// resp.setContentType("text/html");
		// PrintWriter out = resp.getWriter();

		// if (user != null) {
		// out.println("Hello <i>" + user.getNickname() + "</i>!");
		// out.println("[<a href=\""
		// + userService.createLogoutURL(req.getRequestURI())
		// + "\">sign out</a>]");
		// } else {
		// out.println("Hello world! Sign in at: ");
		for (String providerName : openIdProviders.keySet()) {
			String providerUrl = openIdProviders.get(providerName);
			String loginUrl = userService.createLoginURL("http://127.0.0.1:8888/GwtUserGroupSqli.html", null, providerUrl, attributes);
			OpenIdProtocls protocol = new OpenIdProtocls();
			protocol.setName(providerName);
			protocol.setUrl(loginUrl);
			openIdProtols.add(protocol);
			// out.println("[<a href=\"" + loginUrl + "\">" + providerName + "</a>] ");
		}
		return openIdProtols;
		// }
	}
}
