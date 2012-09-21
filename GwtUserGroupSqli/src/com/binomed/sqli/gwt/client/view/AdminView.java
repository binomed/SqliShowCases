package com.binomed.sqli.gwt.client.view;

import java.util.List;

import com.binomed.sqli.gwt.client.presenter.itf.AdminPresenter;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DataGrid;
import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class AdminView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.AdminActivity.Display //
{

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	private final AdminPresenter presenter;

	@UiField(provided = true)
	DataGrid<SqliUserProxy> dataUsers = new DataGrid<SqliUserProxy>(20, GWT.<DataGrid.SelectableResources> create(DataGrid.SelectableResources.class));
	@UiField
	Button remove;

	private final ListDataProvider<SqliUserProxy> dataProvider;
	private final SelectionModel<SqliUserProxy> selectionModel;

	private static final int COL_EMAIL = 0;
	private static final int COL_FIRST_NAME = 1;
	private static final int COL_LAST_NAME = 2;

	public AdminView(AdminPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;

		remove.setEnabled(false);

		// Create columns
		dataUsers.addColumn(new SqliUserColumn(COL_EMAIL), I18N.instance.adminColEmail());
		dataUsers.addColumn(new SqliUserColumn(COL_FIRST_NAME), I18N.instance.adminColFirstName());
		dataUsers.addColumn(new SqliUserColumn(COL_LAST_NAME), I18N.instance.adminColLastName());

		dataUsers.setEmptyTableWidget(new Label(I18N.instance.adminLoadDatas()));

		// Create data provider
		dataProvider = new ListDataProvider<SqliUserProxy>();
		// Connect it to table
		dataProvider.addDataDisplay(dataUsers);

		// Create the selection model
		selectionModel = new SingleSelectionModel(KEY_PROVIDER);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				remove.setEnabled(true);

			}
		});
		// connect it to table
		dataUsers.setSelectionModel(selectionModel);

	}

	public static final ProvidesKey<SqliUserProxy> KEY_PROVIDER = new ProvidesKey<SqliUserProxy>() {

		@Override
		public Object getKey(SqliUserProxy item) {
			return item.getId();
		}
	};

	class SqliUserColumn extends TextColumn<SqliUserProxy> {

		private final int number;

		public SqliUserColumn(int number) {
			super();
			this.number = number;
		}

		@Override
		public String getValue(SqliUserProxy object) {
			switch (number) {
			case COL_EMAIL:
				return object.getEmail();
			case COL_FIRST_NAME:
				return object.getFirstName();
			case COL_LAST_NAME:
				return object.getName();
			default:
				break;
			}
			return "";
		}

	}

	/*
	 * Events Part
	 */

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	public Widget hasWidget() {
		return this;
	}

	@Override
	public void fillUsers(List<SqliUserProxy> userList) {
		dataProvider.getList().clear();
		dataProvider.getList().addAll(userList);
		dataProvider.flush();

		if (dataProvider.getList().isEmpty()) {
			dataUsers.setEmptyTableWidget(new Label(I18N.instance.adminNoUsers()));
		}
		remove.setEnabled(false);
	}

	@UiHandler("remove")
	public void onRemoveClick(ClickEvent event) {
		presenter.deleteUser(((SingleSelectionModel<SqliUserProxy>) selectionModel).getSelectedObject());
	}

}
