package com.binomed.sqli.gwt.client.html5.offline;

import com.binomed.sqli.gwt.client.IClientFactory;
import com.binomed.sqli.gwt.client.event.ui.ConnectionEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author jfgarreau
 * 
 */
public class SqliOfflineManagement {

	private static boolean first = true;
	private static boolean onLine;

	public static void checkConnection(final IClientFactory factory) {

		// Simple ping done every 5 sec
		Timer timer = new Timer() {

			@Override
			public void run() {
				factory.getService().testConnexionService(new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {

						if (!onLine || first) {
							first = false;
							onLine = true;
							factory.getEventBus().fireEvent(new ConnectionEvent(onLine));
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						if (onLine || first) {
							first = false;
							onLine = false;
							factory.getEventBus().fireEvent(new ConnectionEvent(onLine));
						}

					}
				});

			}
		};
		timer.scheduleRepeating(5000);

	}

}
