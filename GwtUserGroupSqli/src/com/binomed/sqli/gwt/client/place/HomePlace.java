package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class HomePlace extends Place {

	@Prefix("home")
	public static class Tokenizer implements PlaceTokenizer<HomePlace> {

		@Override
		public HomePlace getPlace(String token) {
			// Nothing special with home
			return new HomePlace();
		}

		@Override
		public String getToken(HomePlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
