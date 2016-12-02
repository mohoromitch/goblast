package io.mohoromitch.goblast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Singleton for centralized messaging system.
 * Created by Frank on 2016-11-24.
 */
public class NotificationManager {
	public final static String NOTIFICATION_DB_CONNECTED = "dbConnected";
	public final static String NOTIFICATION_LOG = "consoleLog";
	public final static String NOTIFICATION_SQL_ERROR = "sqlError";
	public final static String NOTIFICATION_TABLES_DROPPED = "tablesDropped";
	public final static String NOTIFICATION_TABLES_CREATED = "tablesCreated";
	public final static String NOTIFICATION_VIEWS_DROPPED = "viewsDropped";
	public final static String NOTIFICATION_VIEWS_CREATED = "viewsCreated";
	public final static String NOTIFICATION_UNKOWN_DB_CHANGE = "unknownDbChange";

	private static NotificationManager instance;

	/**
	 * Retrieves the instance of this application.
	 * @return The application's shared manager instance.
	 */
	public synchronized static NotificationManager getSharedInstance() {
		if (instance == null)
			instance = new NotificationManager();
		return instance;
	}

	/**
	 * An interface all listeners must implement to be notified of broadcasts.
	 */
	public interface NotificationListener {
		/**
		 * Called on the reception of broadcasts.
		 * @param tag Broadcast type.
		 * @param payload Broadcast payloa.
		 */
		void onNotification(String tag, Object payload);
	}

	private List<WeakReference<NotificationListener>> listeners = new ArrayList<>();

	/**
	 * Subscribes another listener to the manager.
	 * @param listener The listener to subscribe.
	 */
	public void addListener(NotificationListener listener) {
		listeners.add(new WeakReference<NotificationListener>(listener));
	}

	/**
	 * Sends a broadcast message to all subscribed listeners. All garbage collectors are cleaned at this time aswell.
	 * @param tag Message type/tag.
	 * @param payload Message payload.
	 */
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

	/**
	 * Helper function to simplify the broadcast of logging broadcasts.
	 * @param logMessage Message string. Supports format syntax.
	 * @param args Arguments to pass to the string formater.
	 */
	public synchronized void log(String logMessage, Object...args) {
		notify(NOTIFICATION_LOG, String.format(logMessage, args));
	}
}
