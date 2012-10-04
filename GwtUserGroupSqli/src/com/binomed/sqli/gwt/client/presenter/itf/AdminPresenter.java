package com.binomed.sqli.gwt.client.presenter.itf;

import com.binomed.sqli.gwt.client.presenter.Presenter;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;

/**
 * @author jfgarreau
 * 
 */
public interface AdminPresenter extends Presenter {

	void deleteUser(SqliUserProxy user);

	void switchAdminUser(SqliUserProxy user);

}
