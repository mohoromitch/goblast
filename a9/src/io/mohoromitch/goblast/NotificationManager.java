package io.mohoromitch.goblast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Frank on 2016-11-24.
 */
public class NotificationManager {
	public final static String NOTIFICATION_LOG = "consoleLog";
	public final static String NOTIFICATION_SQL_ERROR = "sqlError";
	public final static String NOTIFICATION_TABLES_DROPPED = "tablesDropped";
	public final static String NOTIFICATION_TABLES_CREATED = "tablesCreated";
	public final static String NOTIFICATION_VIEWS_DROPPED = "viewsDropped";
	public final static String NOTIFICATION_VIEWS_CREATED = "viewsCreated";
	public final static String NOTIFICATION_UNKOWN_DB_CHANGE = "unknownDbChange";

	private static NotificationManager instance;

	public synchronized static NotificationManager getSharedInstance() {
		if (instance == null)
			instance = new NotificationManager();
		return instance;
	}

	public interface NotificationListener {
		void onNotification(String tag, Object payload);
	}
	private List<WeakReference<NotificationListener>> listeners = new ArrayList<>();

	public void addListener(NotificationListener listener) {
		listeners.add(new WeakReference<NotificationListener>(listener));
	}

	public synchronized void notify(String tag, Object payload) {
		Iterator<WeakReference<NotificationListener>> iterator = listeners.iterator();
		while(iterator.hasNext()) {
			WeakReference<NotificationListener> ref = iterator.next();
			if (ref.get() == null)
				iterator.remove();
			else
				ref.get().onNotification(tag, payload);
		}
	}

	public synchronized void log(String logMessage, Object...args) {
		notify(NOTIFICATION_LOG, String.format(logMessage, args));
	}
}
