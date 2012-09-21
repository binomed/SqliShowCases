package com.binomed.sqli.gwt.client.presenter.itf;

import com.binomed.sqli.gwt.client.presenter.Presenter;
import com.google.gwt.event.dom.client.KeyPressEvent;

public interface LoginPresenter extends Presenter {

	void formSubmit();

	void createUser();

	void keyPress(KeyPressEvent event);

}
