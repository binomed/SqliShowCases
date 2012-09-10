package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HomePlace extends Place {

	@Prefix("home")
	public class Tokenizer implements PlaceTokenizer<HomePlace> {

		public HomePlace getPlace(String token) {
			// Nothing special with home
			return new HomePlace();
		}

		public String getToken(HomePlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
