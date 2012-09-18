package com.binomed.sqli.gwt.client.view;

import java.util.Date;

import com.binomed.sqli.gwt.client.presenter.itf.CalendarPresenter;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.api.gwt.services.calendar.shared.model.Event;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class CalendarView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.CalendarActivity.Display //
		, SelectionHandler<Appointment> //
{

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);

	private final CalendarPresenter presenter;
	private final Calendar calendar;
	private int tabIndex;

	private static final String MONDAY = "Mon";

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
		calendar.scrollToHour(9);
		calendar.addSelectionHandler(this);
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

		Date date = event.getValue();
		Date dateToUse = null;
		Date today = new Date();
		int delta = CalendarUtil.getDaysBetween(today, date);
		DateTimeFormat format = DateTimeFormat.getFormat("EEE");
		boolean isMonday = StringUtils.equals(MONDAY, format.format(date));
		int tabIndex = this.tabIndex;
		if (isMonday) {
			dateToUse = date;
		} else if (delta < 7 && delta > 0) {
			dateToUse = date;
			tabIndex = 0;
		} else {

			Date date1 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date1, -1);
			Date date2 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date2, -2);
			Date date3 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date3, -3);
			Date date4 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date4, -4);
			Date date5 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date5, -5);
			Date date6 = CalendarUtil.copyDate(date);
			CalendarUtil.addDaysToDate(date6, -6);

			if (StringUtils.equals(MONDAY, format.format(date1))) {
				dateToUse = date1;
			} else if (StringUtils.equals(MONDAY, format.format(date2))) {
				dateToUse = date2;
			} else if (StringUtils.equals(MONDAY, format.format(date3))) {
				dateToUse = date3;
			} else if (StringUtils.equals(MONDAY, format.format(date4))) {
				dateToUse = date4;
			} else if (StringUtils.equals(MONDAY, format.format(date5))) {
				dateToUse = date5;
			} else if (StringUtils.equals(MONDAY, format.format(date6))) {
				dateToUse = date6;
			}

		}

		manageTabChange(tabIndex, false);
		calendar.setDate(dateToUse);
	}

	@UiHandler("oneDay")
	public void onTab1Day(ClickEvent event) {
		manageTabChange(0, true);
	}

	@UiHandler("threeDay")
	public void onTab3Day(ClickEvent event) {
		manageTabChange(1, true);
	}

	@UiHandler("workWeek")
	public void onTabWorkWeek(ClickEvent event) {
		manageTabChange(2, true);
	}

	@UiHandler("week")
	public void onTabWeek(ClickEvent event) {
		manageTabChange(3, true);
	}

	@UiHandler("month")
	public void onTabMonth(ClickEvent event) {
		manageTabChange(4, true);
	}

	private void manageTabChange(int tabIndex, boolean overrideIndex) {
		if (overrideIndex) {
			this.tabIndex = tabIndex;
		}
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

	@Override
	public void onSelection(SelectionEvent<Appointment> event) {
		presenter.eventClick(event.getSelectedItem());
	}

}
