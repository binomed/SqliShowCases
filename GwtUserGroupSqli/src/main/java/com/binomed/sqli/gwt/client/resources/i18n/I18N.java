package com.binomed.sqli.gwt.client.resources.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface I18N extends Constants {

	public static final I18N instance = GWT.create(I18N.class);

	/* MainWindow */
	String mainTitle();

	String mainSubtitle();

	String mainNavBrand();

	String mainNavSQLI();

	String mainNavAbout();

	String mainNavSupport();

	/* Login View */
	String loginTitle();

	String loginLblLogin();

	String loginHoverLogin();

	String loginLblPassword();

	String loginHoverPassword();

	String loginBtnConnexion();

	String loginLnkNotAccess();

	String loginLnkCreate();

	/* Create View */
	String createTitleForm();

	String createLblName();

	String createHoverFirstName();

	String createHoverLastName();

	String createLblEmail();

	String createHoverEmail();

	String createLblPassword();

	String createLblPasswordConfirm();

	String createCheckContact();

	String createBtnCreate();

	String createTxtWhyTitle();

	String createTxtWhy();

	String createTxtWinTitle();

	String createTxtWin();

}
