package com.binomed.sqli.gwt.server;

import com.binomed.sqli.gwt.client.SqliService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SqliServiceImpl extends RemoteServiceServlet implements SqliService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.sqli.gwt.client.SqliService#testConnexionService()
	 */
	@Override
	public String testConnexionService() {
		return "OK";
	}

}
