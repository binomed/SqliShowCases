package com.binomed.sqli.gwt.client.place;

import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class EditUserPlace extends Place {

	private final SqliUserProxy user;

	public EditUserPlace(SqliUserProxy user) {
		super();
		this.user = user;
	}

	public SqliUserProxy getUser() {
		return user;
	}

	@Prefix("edit")
	public static class Tokenizer implements PlaceTokenizer<EditUserPlace> {

		@Override
		public EditUserPlace getPlace(String token) {
			// Nothing special with home
			return new EditUserPlace(null);
		}

		@Override
		public String getToken(EditUserPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
