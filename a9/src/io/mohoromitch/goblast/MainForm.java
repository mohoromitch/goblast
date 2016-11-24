package io.mohoromitch.goblast;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static io.mohoromitch.goblast.NotificationManager.*;

/**
 * Created by Frank on 2016-11-23.
 */
public class MainForm extends JFrame implements NotificationListener {
	private JPanel panel;
	private JTextPane console;
	private JTextArea commandTextField;
	private Button clearConsoleButton;
	private Button createTablesButton;
	private Button populateTablesButton;
	private Button createViewsButton;
	private Button dropViewsButton;
	private Button dropTablesButton;
	private Button executeCommandButton;
	private Button clearCommandFieldButton;
	private JComboBox tableComboBox;
	private Button outputTableContentsButton;
	private JComboBox comboBox1;
	private Button outputViewContentsButton;

	final static NotificationManager NM = NotificationManager.getSharedInstance();

	public MainForm() {
		super("GOBLAST");
		NotificationManager.getSharedInstance().addListener(this);
		setContentPane(panel);
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		wireButtons();
	}

	public void consoleSet(String message) {
		console.setText(message);
	}

	public void consoleAppend(String message) {
		console.setText(console.getText() + "\n" + message);
	}

	private void wireButtons() {
		clearConsoleButton.addClickAction(() -> {
			System.out.println("Console cleared.");
			consoleSet("");
		});

		createTablesButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.createAllTables();
				nm.notify(NOTIFICATION_TABLES_CREATED, null);
			}
		});

		dropTablesButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.dropAllTables();
				nm.notify(NOTIFICATION_TABLES_DROPPED, null);
			}
		});

		populateTablesButton.addClickAction(() -> {
			consoleAppend("Populate tables button was pressed.");
		});

		createViewsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.createAllViews();
				nm.notify(NOTIFICATION_VIEWS_CREATED, null);
			}
		});
		dropViewsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.dropAllViews();
				nm.notify(NOTIFICATION_VIEWS_CREATED, null);
			}
		});

		executeCommandButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Execute command button was pressed.");
			}
		});

		clearCommandFieldButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				commandTextField.setText("");
				System.out.println("Command field cleared.");
			}
		});

		outputTableContentsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("View table contents button was pressed.");
			}
		});

		outputViewContentsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Output view contents button was pressed.");
			}
		});
	}

	@Override
	public void onNotification(String tag, Object payload) {
		switch (tag) {
			case NOTIFICATION_LOG:
				consoleAppend(payload.toString());
				break;
			case NotificationManager.NOTIFICATION_SQL_ERROR:
				consoleAppend(payload.toString());
				break;
		}
	}
}
