package com.binomed.sqli.gwt.client.resources.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface I18N extends Constants {

	public static final I18N instance = GWT.create(I18N.class);

	/* General */

	String generalFormInvalid();

	/* Calendar */
	String calendarChoose();

	String calendarBtn();

	String calendarMissingAuthorisation();

	String calendarChooseCalendar();

	String calendarAddWell();

	/* MainWindow */
	String mainTitle();

	String mainSubtitle();

	String mainNavBrand();

	String mainNavSQLI();

	String mainNavAbout();

	String mainNavSupport();

	String mainNavAdmin();

	String mainNavEvents();

	String mainNavEdit();

	String mainNavDisconnect();

	String mainConnectionOff();

	/* Login View */
	String loginTitle();

	String loginLblLogin();

	String loginHoverLogin();

	String loginLblPassword();

	String loginHoverPassword();

	String loginBtnConnexion();

	String loginLnkNotAccess();

	String loginLnkCreate();

	String loginVerification();

	String loginEmptyFields();

	String loginUnkown();

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

	String createBtnSave();

	String createLoadMsgCreate();

	String createLoadMsgEdit();

	String createTxtWhyTitle();

	String createTxtWhy();

	String createTxtWinTitle();

	String createTxtWin();

	/* Agenda View */
	String agendaDayOne();

	String agendaDayThree();

	String agendaWorkWeek();

	String agendaWeek();

	String agendaMonth();

	String agendaChat();

	String agendaLoadEvents();

	/* Event View */
	String eventTitle();

	String eventBackEvents();

	String eventSpeaker();

	String eventMainInfo();

	String eventDate();

	String eventHour();

	String eventRoom();

	String eventAddToYourCalendar();

	String eventMap();

	String eventAllDay();

	String eventNoSpeaker();

	/* Admin */
	String adminColEmail();

	String adminColFirstName();

	String adminColLastName();

	String adminColContactMail();

	String adminColIsAdmin();

	String adminLoadDatas();

	String adminNoUsers();

	String adminBtnDelete();

	String adminBtnAdmin();

	String adminBtnNotAdmin();

}
