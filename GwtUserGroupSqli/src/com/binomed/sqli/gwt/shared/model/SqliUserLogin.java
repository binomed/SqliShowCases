package com.binomed.sqli.gwt.shared.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author jfgarreau
 * 
 *         Simple bean use in editor mecanism
 */
public class SqliUserLogin implements Serializable {

	/* Fields */

	@NotNull
	private String password;

	@NotNull
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
