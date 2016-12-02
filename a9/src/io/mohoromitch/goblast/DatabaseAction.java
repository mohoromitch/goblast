package io.mohoromitch.goblast;

import java.sql.SQLException;

/**
 * An abstraction of a button action that does a database operation.
 * Created by Frank on 2016-11-24.
 */
public abstract class DatabaseAction implements ClickAction {

	@Override
	public void onClicked() {
		// When the observable is clicked, prepare a notification manager and database DAO for the query.
		NotificationManager nm = NotificationManager.getSharedInstance();
		try {
			GoblastDAO dao = GoblastDAO.getSharedInstance();
			preformAction(dao, nm);
		} catch (Exception e) {
			nm.notify(NotificationManager.NOTIFICATION_SQL_ERROR, e);
			e.printStackTrace();
		}
	}

	/**
	 * Preform the database transaction.
	 * @param dao Database dao interface.
	 * @param nm Notification Manager to send event boradcasts on.
	 * @throws SQLException Any exception that occured executing sql calls.
	 */
	abstract void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException;
}
