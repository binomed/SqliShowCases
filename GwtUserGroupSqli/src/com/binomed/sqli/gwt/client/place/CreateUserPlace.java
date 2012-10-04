package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class CreateUserPlace extends Place {

	@Prefix("create")
	public static class Tokenizer implements PlaceTokenizer<CreateUserPlace> {

		@Override
		public CreateUserPlace getPlace(String token) {
			// Nothing special with home
			return new CreateUserPlace();
		}

		@Override
		public String getToken(CreateUserPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
