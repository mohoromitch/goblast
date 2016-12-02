package io.mohoromitch.goblast;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * An abstracted button for easy on click observing.
 * Created by Frank on 2016-11-24.
 */
public class Button extends JButton {

	public void addClickAction(ClickAction action) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				action.onClicked();
			}
		});
	}
}
