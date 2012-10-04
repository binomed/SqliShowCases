package com.binomed.sqli.gwt.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author jfgarreau
 * 
 */
public class EventPlace extends Place {

	private final String eventId;

	public String getEventId() {
		return eventId;
	}

	public EventPlace(String eventId) {
		super();
		this.eventId = eventId;
	}

	@Prefix("event")
	public static class Tokenizer implements PlaceTokenizer<EventPlace> {

		@Override
		public EventPlace getPlace(String token) {
			// Nothing special with home
			return new EventPlace(token);
		}

		@Override
		public String getToken(EventPlace place) {
			// Nothing corresponding to the token
			return place.eventId;
		}

	}

}
