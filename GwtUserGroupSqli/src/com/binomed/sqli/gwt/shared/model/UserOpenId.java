package com.binomed.sqli.gwt.shared.model;

import java.io.Serializable;
import java.util.Map;

public class UserOpenId implements Serializable {

	private String redirect;

	private Map params;

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

}
