package com.binomed.sqli.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SqliServiceAsync {

	void testService(AsyncCallback<String> callback);

}
