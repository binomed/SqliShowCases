package com.binomed.sqli.gwt.shared.model;

import java.io.Serializable;

public class OpenIdProtocls implements Serializable {

	private String url;
	private String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
