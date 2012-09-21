package com.binomed.sqli.gwt.client.view;

import java.util.Date;

import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;

public class EventView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.EventActivity.Display //
{

	private static EventViewUiBinder uiBinder = GWT.create(EventViewUiBinder.class);

	private final EventPresenter presenter;
	private static final String DATE_TIME_FORMAT_DATE = "yyyy-MM-dd";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZZZ";
	private static final String DATE_TIME_FORMAT_HOUR = "kk:mm";
	private static final String DATE_TIME_FORMAT_DAY = "EEEE dd MMMM yyyy";

	@UiField
	Heading eventName;

	@UiField
	Paragraph eventDetails;
	@UiField
	ControlLabel eventDate, eventHour, eventRoom;
	@UiField
	FlowPanel mapContainer;

	GoogleMap map;

	public EventView(EventPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;

	}

	interface EventViewUiBinder extends UiBinder<Widget, EventView> {
	}

	public Widget hasWidget() {
		return this;
	}

	@UiHandler("btnAddToCalendar")
	public void onAddToCalendar(ClickEvent event) {
		presenter.addToCalendar();
	}

	@Override
	public void showEvent(Event event) {
		eventName.setText(event.getSummary());
		eventDetails.setText(event.getDescription());
		eventRoom.add(new Label(event.getLocation()));
		if (StringUtils.isEmpty(event.getStart().getDateTime())) {
			Date time = DateTimeFormat.getFormat(DATE_TIME_FORMAT_DATE).parse(event.getStart().getDate());
			eventHour.add(new Label(I18N.instance.eventAllDay()));
			eventDate.add(new Label(DateTimeFormat.getFormat(DATE_TIME_FORMAT_DAY).format(time)));
		} else {
			Date time = DateTimeFormat.getFormat(DATE_TIME_FORMAT).parse(event.getStart().getDateTime());
			eventHour.add(new Label(DateTimeFormat.getFormat(DATE_TIME_FORMAT_HOUR).format(time)));
			eventDate.add(new Label(DateTimeFormat.getFormat(DATE_TIME_FORMAT_DAY).format(time)));
		}

		Geocoder geocoder = Geocoder.create();

		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(17.0);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		map = GoogleMap.create(mapContainer.getElement(), myOptions);

		GeocoderRequest request = GeocoderRequest.create();
		request.setAddress(event.getLocation());
		geocoder.geocode(request, new Geocoder.Callback() {

			@Override
			public void handle(JsArray<GeocoderResult> results, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					GeocoderResult location = results.get(0);
					map.setCenter(location.getGeometry().getLocation());
					MarkerOptions markerOpts = MarkerOptions.create();
					markerOpts.setMap(map);
					markerOpts.setPosition(location.getGeometry().getLocation());
					Marker.create(markerOpts);
				} else {
					Window.alert("Geocode was not successful for the following reason: " + status);
				}
			}
		});

	}
}
