package io.mohoromitch.goblast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Frank on 2016-11-24.
 */
public class Util {

	private final static String PADDING = "                                 ";

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

	private static String pad(String toPad, String padding) {
		//if (toPad.length() > padding.length()) return toPad;
		return toPad + padding.substring(toPad.length());
	}
}
