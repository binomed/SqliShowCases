package com.binomed.sqli.gwt.client.presenter.itf;

import com.binomed.sqli.gwt.client.presenter.Presenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author jfgarreau
 * 
 */
public interface HomePresenter extends Presenter {

	void eventClick(String source);

	void brandClick();

	AcceptsOneWidget getMainPanel();

	void editUser();

	void disconnectUser();

	void goToAdmin();

	void goToEvents();

}
