package com.binomed.sqli.gwt.client.presenter;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.driver.SimpleSqliUserDriver;
import com.binomed.sqli.gwt.client.editor.SimpleSqliUserEditor;
import com.binomed.sqli.gwt.client.event.ui.HideMessageEvent;
import com.binomed.sqli.gwt.client.event.ui.MessageEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserConnectedEvent;
import com.binomed.sqli.gwt.client.event.workflow.UserDisconnectedEvent;
import com.binomed.sqli.gwt.client.handler.workflow.CallBackHiddenMessage;
import com.binomed.sqli.gwt.client.handler.workflow.UserDisconnectedHandler;
import com.binomed.sqli.gwt.client.place.CalendarPlace;
import com.binomed.sqli.gwt.client.place.CreateUserPlace;
import com.binomed.sqli.gwt.client.presenter.itf.LoginPresenter;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.client.utils.StringUtils;
import com.binomed.sqli.gwt.client.view.LoginView;
import com.binomed.sqli.gwt.shared.model.SqliUserLogin;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * @author jfgarreau
 * 
 */
public class LoginActivity implements Activity //
		, LoginPresenter //
		, UserDisconnectedHandler //
{

	/**
	 * @author jfgarreau
	 * 
	 *         Interface for View
	 */
	public interface Display extends IsWidget {

		FlowPanel getUserEditor();

	}

	private final IClientFactory factory;

	private SimpleSqliUserDriver driver;

	public LoginActivity(IClientFactory factory) {
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

		panel.setWidget(new LoginView(this));

		Display view = new LoginView(this);
		panel.setWidget(view);
		// We define the editor and map it
		SimpleSqliUserEditor editor = new SimpleSqliUserEditor(this);
		view.getUserEditor().add(editor);
		driver = new SimpleSqliUserDriver(factory, editor);

		SqliUserProxy curentUser = factory.getConnectedUser();
		if (curentUser != null) {
			driver.desactivForUser(curentUser);
		} else if (factory.getAppStorage().getLastUserLogin() != null) {
			factory.getRequestFactory().userRequest().findUser(factory.getAppStorage().getLastUserLogin()).fire(new Receiver<SqliUserProxy>() {

				@Override
				public void onSuccess(SqliUserProxy response) {
					factory.getEventBus().fireEvent(new UserConnectedEvent(response, true));
					driver.desactivForUser(response);
				}

				@Override
				public void onFailure(ServerFailure error) {
					if (!factory.isConnect()) {
						SqliUserProxy user = factory.getRequestFactory().userRequest().create(SqliUserProxy.class);
						user.setEmail(factory.getAppStorage().getLastUserLogin());
						String[] name = factory.getAppStorage().getUserName();
						if (name != null && name.length == 2) {
							user.setFirstName(name[0]);
							user.setName(name[1]);
						}
						user.setPassword("lorepipsum");
						factory.getEventBus().fireEvent(new UserConnectedEvent(user, false));
						driver.desactivForUser(user);
					}
				}

			});
		}

		factory.getEventBus().addHandler(UserDisconnectedEvent.TYPE, this);

	}

	@Override
	public void formSubmit() {
		SqliUserLogin user = driver.retrieveUser();

		if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {

			factory.getEventBus().fireEvent(new MessageEvent(I18N.instance.loginEmptyFields(), true));
		} else {

			factory.getEventBus().fireEvent(new MessageEvent(I18N.instance.loginVerification(), false));
			Request<SqliUserProxy> request = factory.getRequestFactory().userRequest().verifyUser(user.getEmail(), user.getPassword());
			request.fire(new Receiver<SqliUserProxy>() {

				@Override
				public void onSuccess(SqliUserProxy user) {
					if (user == null) {
						factory.getEventBus().fireEvent(new MessageEvent(I18N.instance.loginUnkown(), true));
					} else {
						factory.getEventBus().fireEvent(new UserConnectedEvent(user, true));
						driver.desactivForUser(user);
						factory.getEventBus().fireEvent(new HideMessageEvent(new CallBackHiddenMessage() {

							@Override
							public void hidden() {
								factory.getPlaceControler().goTo(new CalendarPlace());
							}
						}));
					}

				}

				@Override
				public void onFailure(ServerFailure error) {
					factory.getEventBus().fireEvent(new MessageEvent("Error Verify User: " + error.getMessage(), true));
					super.onFailure(error);
				}
			});
		}

	}

	@Override
	public void createUser() {
		factory.getPlaceControler().goTo(new CreateUserPlace());

	}

	@Override
	public void userDisconnected() {
		driver.activFields();
	}

	@Override
	public void keyPress(KeyPressEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			formSubmit();
		}
	}

}
