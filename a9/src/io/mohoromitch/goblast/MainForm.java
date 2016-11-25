package io.mohoromitch.goblast;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
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
				nm.log("Tables created successfully!");
			}
		});

		dropTablesButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.dropAllTables();
				nm.notify(NOTIFICATION_TABLES_DROPPED, null);
				nm.log("Tables dropped successfully!");
			}
		});

		populateTablesButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.populateTables();
				nm.log("Tables populated successfully!");
			}
		});

		createViewsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.createAllViews();
				nm.notify(NOTIFICATION_VIEWS_CREATED, null);
				nm.notify(NOTIFICATION_VIEWS_CREATED, null);
				nm.log("Views created successfully!");
			}
		});
		dropViewsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				dao.dropAllViews();
				nm.notify(NOTIFICATION_VIEWS_CREATED, null);
				nm.log("Views dropped successfully!");
			}
		});

		executeCommandButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				String sql = commandTextField.getText().trim();
				ResultSet rs = dao.executeRaw(sql);
				nm.log(Util.resultSetToString(rs));
			}
		});

		clearCommandFieldButton.addClickAction(() -> {
			commandTextField.setText("");
		});

		outputTableContentsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				if (tableComboBox.getSelectedItem() != null) {
					String tableName = tableComboBox.getSelectedItem().toString();
					if (tableName != null && !tableName.isEmpty()) {
						nm.log("Loading table contents for %s", tableName);
						ResultSet rs = dao.getAllTableContentsOf(tableName);
						nm.log(Util.resultSetToString(rs));
					}
				} else {
					nm.log("Error: No table was selected!");
				}
			}
		});

		outputViewContentsButton.addClickAction(new DatabaseAction() {
			@Override
			void preformAction(GoblastDAO dao, NotificationManager nm) throws SQLException {
				if (comboBox1.getSelectedItem() != null) {
					String viewName = comboBox1.getSelectedItem().toString();
					if (viewName != null && !viewName.isEmpty()) {
						nm.log("Loading table contents for %s", viewName);
						ResultSet rs = dao.getAllTableContentsOf(viewName);
						nm.log(Util.resultSetToString(rs));
					}
				} else {
					nm.log("Error: No view was selected!");
				}
			}
		});

	}

	private void populateTablesSelection() {
		tableComboBox.removeAllItems();
		try {
			GoblastDAO dao = GoblastDAO.getSharedInstance();
			ResultSet rs = dao.getTableList();
			while(rs.next()) {
				tableComboBox.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateViewsSelection() {
		comboBox1.removeAllItems();
		try {
			GoblastDAO dao = GoblastDAO.getSharedInstance();
			ResultSet rs = dao.getViewList();
			while(rs.next()) {
				comboBox1.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNotification(String tag, Object payload) {
		switch (tag) {
			case NOTIFICATION_DB_CONNECTED:
				NM.log("Database connection established successfully!");
				NM.log("Welcome!");
				populateTablesSelection();
				populateViewsSelection();
				break;
			case NOTIFICATION_TABLES_CREATED:
			case NOTIFICATION_TABLES_DROPPED:
				populateTablesSelection();
				break;
			case NOTIFICATION_VIEWS_CREATED:
			case NOTIFICATION_VIEWS_DROPPED:
				populateViewsSelection();
				break;
			case NOTIFICATION_LOG:
				consoleAppend(payload.toString());
				break;
			case NotificationManager.NOTIFICATION_SQL_ERROR:
				consoleAppend(payload.toString());
				break;
		}
	}
}
