package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SqliUserEditor;
import com.binomed.sqli.gwt.client.event.workflow.UserUpdateEvent;
import com.binomed.sqli.gwt.client.place.EditUserPlace;
import com.binomed.sqli.gwt.client.presenter.itf.EditUserPresenter;
import com.binomed.sqli.gwt.client.view.EditUserView;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class EditUserActivity implements Activity, EditUserPresenter {

	public interface Display extends IsWidget {

		FlowPanel getUserEditor();

	}

	private final IClientFactory factory;
	private final EditUserPlace place;

	private SqliUserDriver driver;

	public EditUserActivity(IClientFactory factory, EditUserPlace place) {
		super();
		this.factory = factory;
		this.place = place;
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
		Display view = new EditUserView(this);
		panel.setWidget(view);
		// We define the editor and map it
		SqliUserEditor editor = new SqliUserEditor(factory, this, true);
		view.getUserEditor().add(editor);
		driver = new SqliUserDriver(factory, editor, place.getUser());

	}

	@Override
	public void formSubmit() {
		driver.save(new SqliUserDriver.CallBackPersist() {

			@Override
			public void persistDone(SqliUserProxy user) {
				factory.getEventBus().fireEvent(new UserUpdateEvent(user));
				factory.getPlaceControler().goTo(factory.getCurrentPlace());
			}
		});

	}
}
