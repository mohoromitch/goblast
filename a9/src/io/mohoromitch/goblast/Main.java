package io.mohoromitch.goblast;

/**
 * Created by Frank on 2016-11-23.
 */
public class Main {
	public static void main(String[] args) {
		MainForm mainForm = new MainForm();
		mainForm.setVisible(true);
		try {
			mainForm.consoleSet("Establishing database connection...");
			GoblastDAO dao = new GoblastDAO(new Database());
			mainForm.consoleAppend("Database connection established successfully!");
			mainForm.consoleAppend("Welcome!");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
