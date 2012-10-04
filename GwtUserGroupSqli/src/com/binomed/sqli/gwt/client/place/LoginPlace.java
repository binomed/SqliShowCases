package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class LoginPlace extends Place {

	@Prefix("login")
	public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

		@Override
		public LoginPlace getPlace(String token) {
			// Nothing special with home
			return new LoginPlace();
		}

		@Override
		public String getToken(LoginPlace place) {
			// Nothing corresponding to the token
			return "home";
		}

	}

}
