package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.presenter.itf.HomePresenter;
import com.binomed.sqli.gwt.client.view.HomeView;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;

public class HomeActivity implements HomePresenter {

	public interface Display {

		AcceptsOneWidget registerMainPanel();

		void showDialog(String source);

	}

	private final IClientFactory factory;

	private final Display view;

	public HomeActivity(IClientFactory factory, HasWidgets panel) {
		super();
		this.factory = factory;
		view = new HomeView(this);
		panel.add((HomeView) view);
	}

	public AcceptsOneWidget getMainPanel() {
		return view.registerMainPanel();
	}

	public void eventClick(String source) {
		view.showDialog(source);

	}

}
