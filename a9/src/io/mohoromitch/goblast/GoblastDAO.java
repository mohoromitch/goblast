package io.mohoromitch.goblast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class GoblastDAO {

	Database database;

	public GoblastDAO(Database database) {
		this.database = database;
	}

	public ResultSet getTableList() throws SQLException {
		return database.execute("SELECT table_name FROM user_tables");
	}

	public ResultSet getViewList() throws SQLException {
		return database.execute("SELECT view_name FROM user_views");
	}

	public ResultSet getAllTableContentsOf(String table) throws SQLException {
		String query = "SELECT * FROM ?";
		PreparedStatement ps = database.getPreparedStatementFrom(query);
		ps.setString(1, "PLAYERS");
		return ps.executeQuery();
	}
}
