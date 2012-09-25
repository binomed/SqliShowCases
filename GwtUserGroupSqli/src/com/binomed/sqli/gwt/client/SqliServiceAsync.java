package com.binomed.sqli.gwt.client;

import java.util.List;

import com.binomed.sqli.gwt.shared.model.OpenIdProtocls;
import com.binomed.sqli.gwt.shared.model.UserOpenId;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SqliServiceAsync {

	void testService(AsyncCallback<String> callback);

	void authenticateOpenId(String provider_url, AsyncCallback<UserOpenId> callback);

	void getOpenIdProtocols(AsyncCallback<List<OpenIdProtocls>> callback);

}
