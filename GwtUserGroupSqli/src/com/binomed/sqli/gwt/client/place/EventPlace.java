package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EventPlace extends Place {

	@Prefix("event")
	public static class Tokenizer implements PlaceTokenizer<EventPlace> {

		@Override
		public EventPlace getPlace(String token) {
			// Nothing special with home
			return new EventPlace();
		}

		@Override
		public String getToken(EventPlace place) {
			// Nothing corresponding to the token
			return "";
		}

	}

}
