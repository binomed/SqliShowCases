package com.binomed.sqli.gwt.client;

import com.binomed.sqli.gwt.client.place.HomePlace;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ HomePlace.Tokenizer.class, LoginPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
