package com.binomed.sqli.gwt.client.html5.storage;

import java.util.ArrayList;
import java.util.List;

import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.api.gwt.services.calendar.shared.model.EventAttendee;
import com.google.api.gwt.services.calendar.shared.model.EventCreator;
import com.google.api.gwt.services.calendar.shared.model.EventDateTime;
import com.google.api.gwt.services.calendar.shared.model.EventExtendedProperties;
import com.google.api.gwt.services.calendar.shared.model.EventGadget;
import com.google.api.gwt.services.calendar.shared.model.EventOrganizer;
import com.google.api.gwt.services.calendar.shared.model.EventReminders;
import com.google.gwt.storage.client.Storage;
import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

public class ImplSqliStorage implements ISqliStorage {

	private final Storage stockStore;

	private static final String KEY_USER_LOGIN = "login";
	private static final String KEY_EVENTS_NUMBER = "eventNumber";
	private static final String KEY_EVENT = "event.";
	private static final String KEY_EVENT_NAME = "name";
	private static final String KEY_EVENT_DESCRIPTION = "description";
	private static final String KEY_EVENT_FULL_DATE = "fullDate";
	private static final String KEY_EVENT_START_DATE = "startDate";
	private static final String KEY_EVENT_END_DATE = "endDate";
	private static final String KEY_EVENT_PLACE = "place";
	private static final String KEY_EVENT_ID = "id";

	public ImplSqliStorage() {
		super();
		stockStore = Storage.getLocalStorageIfSupported();
	}

	@Override
	public void saveUser(SqliUserProxy user) {
		if (stockStore != null) {
			stockStore.setItem(KEY_USER_LOGIN, user.getEmail());
		}

	}

	@Override
	public String getLastUserLogin() {
		String login = null;
		if (stockStore != null) {
			login = stockStore.getItem(KEY_USER_LOGIN);
		}
		return login;
	}

	@Override
	public void removeUserLogin() {
		if (stockStore != null) {
			stockStore.removeItem(KEY_USER_LOGIN);
		}
	}

	@Override
	public List<Event> getListEvents() {
		List<Event> eventList = new ArrayList<Event>();
		if (stockStore != null) {
			if (StringUtils.isNotEmpty(stockStore.getItem(KEY_EVENTS_NUMBER))) {
				int nbEvents = Integer.valueOf(stockStore.getItem(KEY_EVENTS_NUMBER));
				for (int i = 0; i < nbEvents; i++) {
					eventList.add(getEvent(i));
				}
			}
		}
		return eventList;
	}

	@Override
	public Event getEvent(int i) {
		Event event = null;
		if (stockStore != null) {
			EventDateTime start = null;
			EventDateTime end = null;
			event = new SqliEvent();
			event.setId(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_ID));
			event.setSummary(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_NAME));
			event.setDescription(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_DESCRIPTION));
			start = new SqliEventDateTime();
			end = new SqliEventDateTime();
			event.setStart(start);
			event.setEnd(end);
			if (Boolean.valueOf(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_FULL_DATE))) {
				start.setDate(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_START_DATE));
				end.setDate(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_END_DATE));
			} else {
				start.setDateTime(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_START_DATE));
				end.setDateTime(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_END_DATE));

			}
			event.setLocation(stockStore.getItem(KEY_EVENT + i + KEY_EVENT_PLACE));
		}
		return event;
	}

	@Override
	public void saveListEvents(List<Event> events) {
		if (stockStore != null) {
			int nbEvents = 0;
			if (StringUtils.isNotEmpty(stockStore.getItem(KEY_EVENTS_NUMBER))) {
				nbEvents = Integer.valueOf(stockStore.getItem(KEY_EVENTS_NUMBER));
			}
			int i = nbEvents;
			nbEvents += events.size();
			for (Event event : events) {
				stockStore.setItem(KEY_EVENT + i + KEY_EVENT_ID, event.getId());
				// We stock the number in order to extract it easly
				stockStore.setItem(KEY_EVENT + event.getId(), String.valueOf(i));
				stockStore.setItem(KEY_EVENT + i + KEY_EVENT_NAME, event.getSummary());
				stockStore.setItem(KEY_EVENT + i + KEY_EVENT_DESCRIPTION, event.getDescription());
				boolean fullDate = StringUtils.isNotEmpty(event.getStart().getDate());
				stockStore.setItem(KEY_EVENT + i + KEY_EVENT_FULL_DATE, String.valueOf(fullDate));
				if (fullDate) {
					stockStore.setItem(KEY_EVENT + i + KEY_EVENT_START_DATE, event.getStart().getDate());
					stockStore.setItem(KEY_EVENT + i + KEY_EVENT_END_DATE, event.getEnd().getDate());
				} else {
					stockStore.setItem(KEY_EVENT + i + KEY_EVENT_START_DATE, event.getStart().getDateTime());
					stockStore.setItem(KEY_EVENT + i + KEY_EVENT_END_DATE, event.getEnd().getDateTime());

				}
				stockStore.setItem(KEY_EVENT + i + KEY_EVENT_PLACE, event.getLocation());
				i++;
			}
		}

	}

	@Override
	public int getNbEvents() {
		int result = 0;
		if (stockStore != null && StringUtils.isNotEmpty(stockStore.getItem(KEY_EVENTS_NUMBER))) {
			result = Integer.valueOf(stockStore.getItem(KEY_EVENTS_NUMBER));
		}
		return result;
	}

	@Override
	public int getEventNumber(String idEvent) {
		int result = -1;
		if (stockStore != null) {
			if (StringUtils.isNotEmpty(stockStore.getItem(KEY_EVENT + idEvent))) {
				result = Integer.valueOf(stockStore.getItem(KEY_EVENT + idEvent));
			}
		}
		return result;
	}

	static class SqliEvent implements Event {

		private String id;
		private EventDateTime start;
		private EventDateTime end;
		private String location;
		private String summary;
		private String description;

		@Override
		@PropertyName("id")
		public String getId() {
			return id;
		}

		@Override
		@PropertyName("id")
		public Event setId(String id) {
			this.id = id;
			return this;
		}

		@Override
		@PropertyName("start")
		public EventDateTime getStart() {
			return start;
		}

		@Override
		@PropertyName("start")
		public Event setStart(EventDateTime start) {
			this.start = start;
			return this;
		}

		@Override
		@PropertyName("location")
		public String getLocation() {
			return location;
		}

		@Override
		@PropertyName("location")
		public Event setLocation(String location) {
			this.location = location;
			return this;
		}

		@Override
		@PropertyName("description")
		public String getDescription() {
			return description;
		}

		@Override
		@PropertyName("description")
		public Event setDescription(String description) {
			this.description = description;
			return this;
		}

		@Override
		@PropertyName("end")
		public EventDateTime getEnd() {
			return end;
		}

		@Override
		@PropertyName("end")
		public Event setEnd(EventDateTime end) {
			this.end = end;
			return this;
		}

		@Override
		@PropertyName("summary")
		public String getSummary() {
			return summary;
		}

		@Override
		@PropertyName("summary")
		public Event setSummary(String summary) {
			this.summary = summary;
			return this;
		}

		/*
		 * Unused vars
		 */

		@Override
		@PropertyName("creator")
		public EventCreator getCreator() {
			return null;
		}

		@Override
		@PropertyName("creator")
		public Event setCreator(EventCreator creator) {
			return null;
		}

		@Override
		@PropertyName("organizer")
		public EventOrganizer getOrganizer() {
			return null;
		}

		@Override
		@PropertyName("organizer")
		public Event setOrganizer(EventOrganizer organizer) {
			return null;
		}

		@Override
		@PropertyName("attendees")
		public List<EventAttendee> getAttendees() {
			return null;
		}

		@Override
		@PropertyName("attendees")
		public Event setAttendees(List<EventAttendee> attendees) {
			return null;
		}

		@Override
		@PropertyName("htmlLink")
		public String getHtmlLink() {
			return null;
		}

		@Override
		@PropertyName("htmlLink")
		public Event setHtmlLink(String htmlLink) {
			return null;
		}

		@Override
		@PropertyName("recurrence")
		public List<String> getRecurrence() {
			return null;
		}

		@Override
		@PropertyName("recurrence")
		public Event setRecurrence(List<String> recurrence) {
			return null;
		}

		@Override
		@PropertyName("etag")
		public String getEtag() {
			return null;
		}

		@Override
		@PropertyName("etag")
		public Event setEtag(String etag) {
			return null;
		}

		@Override
		@PropertyName("recurringEventId")
		public String getRecurringEventId() {
			return null;
		}

		@Override
		@PropertyName("recurringEventId")
		public Event setRecurringEventId(String recurringEventId) {
			return null;
		}

		@Override
		@PropertyName("originalStartTime")
		public EventDateTime getOriginalStartTime() {
			return null;
		}

		@Override
		@PropertyName("originalStartTime")
		public Event setOriginalStartTime(EventDateTime originalStartTime) {
			return null;
		}

		@Override
		@PropertyName("status")
		public String getStatus() {
			return null;
		}

		@Override
		@PropertyName("status")
		public Event setStatus(String status) {
			return null;
		}

		@Override
		@PropertyName("updated")
		public String getUpdated() {
			return null;
		}

		@Override
		@PropertyName("updated")
		public Event setUpdated(String updated) {
			return null;
		}

		@Override
		@PropertyName("gadget")
		public EventGadget getGadget() {
			return null;
		}

		@Override
		@PropertyName("gadget")
		public Event setGadget(EventGadget gadget) {
			return null;
		}

		@Override
		@PropertyName("iCalUID")
		public String getICalUID() {
			return null;
		}

		@Override
		@PropertyName("iCalUID")
		public Event setICalUID(String iCalUID) {
			return null;
		}

		@Override
		@PropertyName("extendedProperties")
		public EventExtendedProperties getExtendedProperties() {
			return null;
		}

		@Override
		@PropertyName("extendedProperties")
		public Event setExtendedProperties(EventExtendedProperties extendedProperties) {
			return null;
		}

		@Override
		@PropertyName("sequence")
		public Integer getSequence() {
			return null;
		}

		@Override
		@PropertyName("sequence")
		public Event setSequence(Integer sequence) {
			return null;
		}

		@Override
		@PropertyName("visibility")
		public String getVisibility() {
			return null;
		}

		@Override
		@PropertyName("visibility")
		public Event setVisibility(String visibility) {
			return null;
		}

		@Override
		@PropertyName("guestsCanModify")
		public Boolean getGuestsCanModify() {
			return null;
		}

		@Override
		@PropertyName("guestsCanModify")
		public Event setGuestsCanModify(Boolean guestsCanModify) {
			return null;
		}

		@Override
		@PropertyName("attendeesOmitted")
		public Boolean getAttendeesOmitted() {
			return null;
		}

		@Override
		@PropertyName("attendeesOmitted")
		public Event setAttendeesOmitted(Boolean attendeesOmitted) {
			return null;
		}

		@Override
		@PropertyName("kind")
		public String getKind() {
			return null;
		}

		@Override
		@PropertyName("kind")
		public Event setKind(String kind) {
			return null;
		}

		@Override
		@PropertyName("created")
		public String getCreated() {
			return null;
		}

		@Override
		@PropertyName("created")
		public Event setCreated(String created) {
			return null;
		}

		@Override
		@PropertyName("colorId")
		public String getColorId() {
			return null;
		}

		@Override
		@PropertyName("colorId")
		public Event setColorId(String colorId) {
			return null;
		}

		@Override
		@PropertyName("anyoneCanAddSelf")
		public Boolean getAnyoneCanAddSelf() {
			return null;
		}

		@Override
		@PropertyName("anyoneCanAddSelf")
		public Event setAnyoneCanAddSelf(Boolean anyoneCanAddSelf) {
			return null;
		}

		@Override
		@PropertyName("reminders")
		public EventReminders getReminders() {
			return null;
		}

		@Override
		@PropertyName("reminders")
		public Event setReminders(EventReminders reminders) {
			return null;
		}

		@Override
		@PropertyName("guestsCanSeeOtherGuests")
		public Boolean getGuestsCanSeeOtherGuests() {
			return null;
		}

		@Override
		@PropertyName("guestsCanSeeOtherGuests")
		public Event setGuestsCanSeeOtherGuests(Boolean guestsCanSeeOtherGuests) {
			return null;
		}

		@Override
		@PropertyName("guestsCanInviteOthers")
		public Boolean getGuestsCanInviteOthers() {
			return null;
		}

		@Override
		@PropertyName("guestsCanInviteOthers")
		public Event setGuestsCanInviteOthers(Boolean guestsCanInviteOthers) {
			return null;
		}

		@Override
		@PropertyName("transparency")
		public String getTransparency() {
			return null;
		}

		@Override
		@PropertyName("transparency")
		public Event setTransparency(String transparency) {
			return null;
		}

		@Override
		@PropertyName("privateCopy")
		public Boolean getPrivateCopy() {
			return null;
		}

		@Override
		@PropertyName("privateCopy")
		public Event setPrivateCopy(Boolean privateCopy) {
			return null;
		}

	}

	static class SqliEventDateTime implements EventDateTime {

		private String date;
		private String dateTime;

		@Override
		@PropertyName("date")
		public String getDate() {
			return date;
		}

		@Override
		@PropertyName("date")
		public EventDateTime setDate(String date) {
			this.date = date;
			return this;
		}

		@Override
		@PropertyName("timeZone")
		public String getTimeZone() {
			return null;
		}

		@Override
		@PropertyName("timeZone")
		public EventDateTime setTimeZone(String timeZone) {
			return null;
		}

		@Override
		@PropertyName("dateTime")
		public String getDateTime() {
			return dateTime;
		}

		@Override
		@PropertyName("dateTime")
		public EventDateTime setDateTime(String dateTime) {
			this.dateTime = dateTime;
			return this;
		}

	}

}
