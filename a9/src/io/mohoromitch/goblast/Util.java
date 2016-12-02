package io.mohoromitch.goblast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * A utility class containing methods that shorten implementation in other areas.
 * Created by Frank on 2016-11-24.
 */
public class Util {

	/** Default padding. **/
	private final static String PADDING = "                                 ";

	/**
	 * Turns a ResultSet into a nice looking string table.
	 * @param rs ResultSet object.
	 * @return A String representation of the ResultSet.
	 * @throws SQLException Any Sql error in iterating through the ResultSet.
	 */
	public static String resultSetToString(ResultSet rs) throws SQLException {
		StringBuilder text = new StringBuilder();
		ResultSetMetaData rsmd = rs.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			text.append(pad(rsmd.getColumnName(i), PADDING));
		}
		text.append("\n");

		while(rs.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				text.append(pad(rs.getString(i), PADDING));
			}
			text.append("\n");
		}

		return text.toString();
	}

	/**
	 * Padds strings to a provided width.
	 * @param toPad String to pad.
	 * @param padding String who's length denotes the pad width, and characters denote the padding characters respectively.
	 * @return A padded String.
	 */
	private static String pad(String toPad, String padding) {
		if (toPad.length() > padding.length()) return toPad;
		return toPad + padding.substring(toPad.length());
	}
}
