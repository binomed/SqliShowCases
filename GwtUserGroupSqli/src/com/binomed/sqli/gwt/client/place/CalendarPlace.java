package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class CalendarPlace extends Place {

	@Prefix("calendar")
	public static class Tokenizer implements PlaceTokenizer<CalendarPlace> {

		@Override
		public CalendarPlace getPlace(String token) {
			// Nothing special with home
			return new CalendarPlace();
		}

		@Override
		public String getToken(CalendarPlace place) {
			// Nothing corresponding to the token
			return "events";
		}

	}

}
