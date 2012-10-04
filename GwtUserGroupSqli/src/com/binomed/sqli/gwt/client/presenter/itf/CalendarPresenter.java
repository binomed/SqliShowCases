package com.binomed.sqli.gwt.client.presenter.itf;

import com.binomed.sqli.gwt.client.presenter.Presenter;
import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * @author jfgarreau
 * 
 */
public interface CalendarPresenter extends Presenter {

	void eventClick(Appointment event);

}
