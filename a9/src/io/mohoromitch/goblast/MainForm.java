package io.mohoromitch.goblast;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Frank on 2016-11-23.
 */
public class MainForm extends JFrame {
	private JPanel panel;
	private JTextPane console;
	private JTextArea commandTextField;
	private JButton clearConsoleButton;
	private JButton createTablesButton;
	private JButton populateTablesButton;
	private JButton createViewsButton;
	private JButton dropViewsButton;
	private JButton dropTablesButton;
	private JButton executeCommandButton;
	private JButton clearCommandFieldButton;
	private JComboBox tableComboBox;
	private JButton outputTableContentsButton;
	private JComboBox comboBox1;
	private JButton outputViewContentsButton;

	public MainForm() {
		super("GOBLAST");
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
		clearConsoleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("Console cleared.");
				consoleSet("");
			}
		});

		createTablesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Create tables button was pressed.");
			}
		});

		populateTablesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Populate tables button was pressed.");
			}
		});

		createViewsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Create views button was pressed.");
			}
		});

		dropViewsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Drop views button was pressed.");
			}
		});

		dropTablesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				consoleAppend("Drop tables button was pressed.");
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
}
