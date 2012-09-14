package com.binomed.sqli.gwt.client.view;

import java.util.Date;

import com.binomed.sqli.gwt.client.presenter.itf.CalendarPresenter;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class CalendarView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.CalendarActivity.Display //
		, IsWidget//
{

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);

	private final CalendarPresenter presenter;
	private final Calendar calendar;

	@UiField
	FlowPanel calendarContent;

	@UiField
	DateBox datePicker;

	public CalendarView(CalendarPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;

		calendar = new Calendar();
		calendar.setDate(new Date()); // calendar date, not required
		calendar.setWidth("100%");
		calendar.setView(CalendarViews.DAY, 7);
		calendar.setHeight("100%");
		calendarContent.add(calendar);

	}

	@Override
	public void addEvent(Event event) {
		if (event.getStart().getDateTime() != null && event.getStart().getDateTime().length() > 0) {
			Appointment appointment = new Appointment();
			appointment.setStyle(AppointmentStyle.ORANGE);
			appointment.setStart(DateTimeFormat.getFormat(DATE_TIME_FORMAT).parse(event.getStart().getDateTime()));
			appointment.setEnd(DateTimeFormat.getFormat(DATE_TIME_FORMAT).parse(event.getEnd().getDateTime()));
			appointment.setTitle(event.getSummary());
			calendar.addAppointment(appointment);
		}

	}

	/*
	 * Events Part
	 */

	@UiHandler("datePicker")
	public void onChangeDate(ValueChangeEvent<Date> event) {
		calendar.setDate(event.getValue());
	}

	@UiHandler("oneDay")
	public void onTab1Day(ClickEvent event) {
		manageTabChange(0);
	}

	@UiHandler("threeDay")
	public void onTab3Day(ClickEvent event) {
		manageTabChange(1);
	}

	@UiHandler("workWeek")
	public void onTabWorkWeek(ClickEvent event) {
		manageTabChange(2);
	}

	@UiHandler("week")
	public void onTabWeek(ClickEvent event) {
		manageTabChange(3);
	}

	@UiHandler("month")
	public void onTabMonth(ClickEvent event) {
		manageTabChange(4);
	}

	private void manageTabChange(int tabIndex) {
		if (tabIndex == 0) {
			calendar.setView(CalendarViews.DAY, 1);
		} else if (tabIndex == 1) {
			calendar.setView(CalendarViews.DAY, 3);
		} else if (tabIndex == 2) {
			calendar.setView(CalendarViews.DAY, 5);
		} else if (tabIndex == 3) {
			calendar.setView(CalendarViews.DAY, 7);
		} else if (tabIndex == 4) {
			calendar.setView(CalendarViews.MONTH);
		}
	}

	interface CalendarViewUiBinder extends UiBinder<Widget, CalendarView> {
	}

	public Widget hasWidget() {
		return this;
	}

}
