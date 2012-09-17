package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.place.LoginPlace;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.view.HomeView;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;

public class HomeActivity implements HomePresenter {

	public interface Display {

		AcceptsOneWidget registerMainPanel();

		void showDialog(String source);

		void showLoadMessage(String message);

		void hideLoadMessage();

	}

	private final IClientFactory factory;

	private final Display view;

	public HomeActivity(IClientFactory factory, HasWidgets panel) {
		super();
		this.factory = factory;
		view = new HomeView(this);
		panel.add((HomeView) view);
	}

	@Override
	public AcceptsOneWidget getMainPanel() {
		return view.registerMainPanel();
	}

	@Override
	public void eventClick(String source) {
		view.showDialog(source);

	}

	@Override
	public void brandClick() {
		factory.getPlaceControler().goTo(new LoginPlace());

	}

	@Override
	public void showLoadMessage(String message) {
		view.showLoadMessage(message);
	}

	@Override
	public void hideLoadMessage() {
		view.hideLoadMessage();

	}

}
