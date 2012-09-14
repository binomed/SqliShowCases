package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CalendarPlace extends Place {

	@Prefix("calendar")
	public static class Tokenizer implements PlaceTokenizer<CalendarPlace> {

		public CalendarPlace getPlace(String token) {
			// Nothing special with home
			return new CalendarPlace();
		}

		public String getToken(CalendarPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
