package io.mohoromitch.goblast;

import java.sql.ResultSet;

/**
 * Created by Frank on 2016-11-24.
 */
public class SqlAction implements ClickAction {

	interface OnResultCb {
		void onResult();
	}

	final String QUERY, NOTIFICATION;
	final OnResultCb CB;

	public SqlAction(String query, String notificationTag) {
		this(query, notificationTag, null);
	}

	public SqlAction(String query, String notificationTag, OnResultCb cb) {
		QUERY = query;
		NOTIFICATION = notificationTag;
		CB = cb;
	}

	@Override
	public void onClicked() {
		try {
			GoblastDAO.getSharedInstance().database.execute(QUERY);
			NotificationManager.getSharedInstance().notify(NOTIFICATION, null);
			if (CB != null) {
				CB.onResult();
			}
		} catch (Exception e) {
			NotificationManager.getSharedInstance().notify(NotificationManager.NOTIFICATION_SQL_ERROR, e);
			e.printStackTrace();
		}
	}
}
