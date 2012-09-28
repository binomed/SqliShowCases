package com.binomed.sqli.gwt.client;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.OpenIdProtocls;
import com.binomed.sqli.gwt.shared.model.UserOpenId;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("sqli")
public interface SqliService extends RemoteService {

	String testConnexionService();

	UserOpenId authenticateOpenId(String provider_url);

	List<OpenIdProtocls> getOpenIdProtocols();
}
