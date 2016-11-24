package io.mohoromitch.goblast;

import java.sql.SQLException;

/**
 * Created by Frank on 2016-11-24.
 */
public abstract class DatabaseAction implements ClickAction {

	@Override
	public void onClicked() {
		NotificationManager nm = NotificationManager.getSharedInstance();
		try {
			GoblastDAO dao = GoblastDAO.getSharedInstance();
			preformAction(dao, nm);
		} catch (Exception e) {
			nm.notify(NotificationManager.NOTIFICATION_SQL_ERROR, e);
			e.printStackTrace();
		}
	}

	abstract void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException;
}
