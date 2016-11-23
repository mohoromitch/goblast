package io.mohoromitch.goblast;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Frank on 2016-11-23.
 */
public class MainForm extends JFrame {
	private JButton testButton;
	private JPanel panel1;

	public MainForm() {
		super("GOBLAST");
		setContentPane(panel1);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// We should make each triggered event an action, that wraps the sql and model changes for maintainability
		testButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("Test");
			}
		});
	}
}
