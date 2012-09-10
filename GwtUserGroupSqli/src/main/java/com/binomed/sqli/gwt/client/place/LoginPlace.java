package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class LoginPlace extends Place {

	@Prefix("login")
	public class Tokenizer implements PlaceTokenizer<LoginPlace> {

		public LoginPlace getPlace(String token) {
			// Nothing special with home
			return new LoginPlace();
		}

		public String getToken(LoginPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
