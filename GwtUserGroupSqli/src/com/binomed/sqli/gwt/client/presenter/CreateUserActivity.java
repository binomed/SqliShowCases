package com.binomed.sqli.gwt.client.presenter;

import java.util.List;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.place.CalendarPlace;
import com.binomed.sqli.gwt.client.presenter.itf.CreateUserPresenter;
import com.binomed.sqli.gwt.client.view.CreateUserView;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class CreateUserActivity implements Activity, CreateUserPresenter {

	public interface Display extends IsWidget {

		FlowPanel getUserEditor();

	}

	private final IClientFactory factory;

	private SqliUserDriver driver;

	public CreateUserActivity(IClientFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Display view = new CreateUserView(this);
		panel.setWidget(view);
		// We define the editor and map it
		SqliUserEditor editor = new SqliUserEditor(factory, this, false);
		view.getUserEditor().add(editor);
		driver = new SqliUserDriver(factory, editor, null);

		factory.getRequestFactory().userRequest().findAllUsers().fire(new Receiver<List<SqliUserProxy>>() {

			@Override
			public void onSuccess(List<SqliUserProxy> response) {
				driver.setFirstUser(response.size() == 0);

			}
		});

	}

	@Override
	public void formSubmit() {
		driver.save(new SqliUserDriver.CallBackPersist() {

			@Override
			public void persistDone(SqliUserProxy user) {
				factory.getEventBus().fireEvent(new UserConnectedEvent(user, true));
				factory.getPlaceControler().goTo(new CalendarPlace());

			}
		});

	}
}
