package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AdminPlace extends Place {

	public AdminPlace() {
		super();
	}

	@Prefix("admin")
	public static class Tokenizer implements PlaceTokenizer<AdminPlace> {

		@Override
		public AdminPlace getPlace(String token) {
			// Nothing special with home
			return new AdminPlace();
		}

		@Override
		public String getToken(AdminPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
