package io.mohoromitch.goblast;

/**
 * The program's entry point.
 * Created by Frank on 2016-11-23.
 */
public class Main {

	public static void main(String[] args) {
		// Initiate singletons, initilize and show ui, then connect to the database
		NotificationManager nm = NotificationManager.getSharedInstance();
		MainForm mainForm = new MainForm();
		mainForm.setVisible(true);
		try {
			//mainForm.consoleSet("Establishing database connection...");
			nm.log("Establishing database connection...");
			GoblastDAO dao = GoblastDAO.getSharedInstance();
			nm.notify(NotificationManager.NOTIFICATION_DB_CONNECTED, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
