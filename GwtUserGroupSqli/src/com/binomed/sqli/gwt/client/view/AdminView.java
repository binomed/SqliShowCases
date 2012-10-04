package com.binomed.sqli.gwt.client.view;

import java.util.List;
import java.util.logging.Logger;

import com.binomed.sqli.gwt.client.presenter.itf.AdminPresenter;
import com.binomed.sqli.gwt.client.resources.i18n.I18N;
import com.binomed.sqli.gwt.shared.model.SqliUserProxy;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DataGrid;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Pagination;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author jfgarreau
 * 
 */
public class AdminView extends Composite implements //
		com.binomed.sqli.gwt.client.presenter.AdminActivity.Display //
{

	private static final Logger LOGGER = Logger.getLogger("AdminView");

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	private final AdminPresenter presenter;

	@UiField(provided = true)
	DataGrid<SqliUserProxy> dataUsers = new DataGrid<SqliUserProxy>(20, GWT.<DataGrid.SelectableResources> create(DataGrid.SelectableResources.class));
	@UiField
	Button remove;
	@UiField
	Button switchAdmin;
	@UiField
	Pagination pagination;

	private final ListDataProvider<SqliUserProxy> dataProvider;
	private final SelectionModel<SqliUserProxy> selectionModel;
	private SimplePager pager = new SimplePager();

	private static final int COL_EMAIL = 0;
	private static final int COL_FIRST_NAME = 1;
	private static final int COL_LAST_NAME = 2;
	private static final int COL_CONTACT_MAIL = 3;
	private static final int COL_IS_ADMIN = 4;

	public AdminView(AdminPresenter presenter) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;

		remove.setEnabled(false);
		switchAdmin.setEnabled(false);

		// Create columns
		dataUsers.addColumn(new SqliUserColumn(COL_EMAIL), I18N.instance.adminColEmail());
		dataUsers.addColumn(new SqliUserColumn(COL_FIRST_NAME), I18N.instance.adminColFirstName());
		dataUsers.addColumn(new SqliUserColumn(COL_LAST_NAME), I18N.instance.adminColLastName());
		dataUsers.addColumn(new SqliUserColumn(COL_CONTACT_MAIL), I18N.instance.adminColContactMail());
		dataUsers.addColumn(new SqliUserColumn(COL_IS_ADMIN), I18N.instance.adminColIsAdmin());

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
				switchAdmin.setEnabled(true);
				SqliUserProxy user = ((SingleSelectionModel<SqliUserProxy>) event.getSource()).getSelectedObject();
				switchAdmin.setText(user.isAdmin() ? I18N.instance.adminBtnNotAdmin() : I18N.instance.adminBtnAdmin());

			}
		});
		// connect it to table
		dataUsers.setSelectionModel(selectionModel);

		// Connect pager
		pager.setDisplay(dataUsers);
		pagination.clear();

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
			case COL_CONTACT_MAIL:
				return String.valueOf(object.isContactAllowed());
			case COL_IS_ADMIN:
				return String.valueOf(object.isAdmin());
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

		// Refresh pager
		pagination.clear();
		if (pager.getPageCount() == 0) {
			return;
		}
		NavLink prev = new NavLink("<");

		prev.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWT.log(String.valueOf("prev"));
				pager.previousPage();
			}
		});

		prev.setDisabled(!pager.hasPreviousPage());

		pagination.add(prev);

		int before = 2;
		int after = 2;

		while (!pager.hasPreviousPages(before) && before > 0) {
			before--;
			if (pager.hasNextPages(after + 1)) {
				after++;
			}
		}

		while (!pager.hasNextPages(after) && after > 0) {
			after--;
			if (pager.hasPreviousPages(before + 1)) {
				before++;
			}
		}

		for (int i = pager.getPage() - before; i <= pager.getPage() + after; i++) {

			final int index = i + 1;

			NavLink page = new NavLink(String.valueOf(index));

			page.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					pager.setPage(index - 1);
				}
			});

			if (i == pager.getPage()) {
				page.setActive(true);
			}

			pagination.add(page);
		}

		NavLink next = new NavLink(">");

		next.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWT.log(String.valueOf("next"));
				pager.nextPage();
			}
		});

		next.setDisabled(!pager.hasNextPage());

		pagination.add(next);
	}

	@UiHandler("remove")
	public void onRemoveClick(ClickEvent event) {
		presenter.deleteUser(((SingleSelectionModel<SqliUserProxy>) selectionModel).getSelectedObject());
	}

	@UiHandler("switchAdmin")
	public void onSwitchAdminClick(ClickEvent event) {
		presenter.switchAdminUser(((SingleSelectionModel<SqliUserProxy>) selectionModel).getSelectedObject());
	}

}
