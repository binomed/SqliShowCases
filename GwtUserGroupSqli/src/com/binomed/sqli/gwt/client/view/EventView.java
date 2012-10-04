package com.binomed.sqli.gwt.client.view;

import java.util.Date;
import java.util.Iterator;

import com.binomed.sqli.gwt.client.presenter.itf.EventPresenter;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.client.utils.Constants;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.client.widget.Speaker;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Heading;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.common.base.Splitter;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
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

/**
 * @author jfgarreau
 * 
 */
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
	HTML eventDetails;
	@UiField
	ControlLabel eventDate, eventHour, eventRoom;
	@UiField
	FlowPanel mapContainer;
	@UiField
	HTMLPanel speakers;

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

		String description = event.getDescription();
		String details = null;
		Widget speakerContent = null;

		Iterator<String> splitDesc = Splitter.on(Constants.SPEAKER_BREAKER).trimResults().split(description).iterator();
		details = splitDesc.next();
		if (splitDesc.hasNext()) {
			String urlImg = null;
			String detailsSpeaker = null;
			String speakerTxt = null;
			int indexStart = -1;
			int indexEnd = -1;
			while (splitDesc.hasNext()) {
				urlImg = null;
				detailsSpeaker = null;
				speakerTxt = splitDesc.next();
				indexStart = speakerTxt.indexOf(Constants.SPEAKER_IMG_TAG);
				indexEnd = speakerTxt.indexOf(Constants.SPEAKER_IMG_TAG_END);
				if (indexStart != -1) {
					urlImg = speakerTxt.substring(indexStart + Constants.SPEAKER_IMG_TAG.length(), indexEnd);
				}
				indexStart = speakerTxt.indexOf(Constants.SPEAKER_RESUME_TAG);
				indexEnd = speakerTxt.indexOf(Constants.SPEAKER_RESUME_TAG_END);
				if (indexStart != -1) {
					detailsSpeaker = speakerTxt.substring(indexStart + Constants.SPEAKER_RESUME_TAG.length(), indexEnd);
				}
				speakerContent = new Speaker(urlImg, detailsSpeaker);

			}
		} else {
			speakerContent = new com.github.gwtbootstrap.client.ui.Label(I18N.instance.eventNoSpeaker());
		}

		eventName.setText(event.getSummary());
		eventDetails.setHTML(details);
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
		speakers.add(speakerContent);
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
