package io.mohoromitch.goblast;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interface defining an onClick listener.
 * Created by Frank on 2016-11-24.
 */
public interface ClickAction {
	/**
	 * Called when an attached observable is clicked.
	 */
	void onClicked();
}
