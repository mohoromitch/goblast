package io.mohoromitch.goblast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class GoblastDAO {

	private static GoblastDAO instance = null;

	public static synchronized GoblastDAO getSharedInstance() throws Exception {
		if (instance == null)
			instance = new GoblastDAO(new Database());
		return instance;
	}

	Database database;

	private GoblastDAO(Database database) {
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

	public void createAllTables() throws SQLException {
		database.executeBatch(Queries.C_TABLES);
		/*
		Statement s = database.createStatement();
		Queries.addBatchQuery(Queries.C_TABLES, s);
		s.executeBatch();
		*/
	}

	public void dropAllTables() throws SQLException {
		database.executeBatch(Queries.D_TABLES);
		/*
		Statement s = database.createStatement();
		Queries.addBatchQuery(Queries.D_TABLES, s);
		s.executeBatch();
		*/
	}

	public void populateTables() throws SQLException {
		database.execute(Queries.P_TABLES);
	}

	public void createAllViews() throws SQLException {
		Statement s = database.createStatement();
		Queries.addBatchQuery(Queries.C_VIEWS, s);
		s.executeBatch();
	}

	public void dropAllViews() throws SQLException {
		Statement s = database.createStatement();
		Queries.addBatchQuery(Queries.D_VIEWS, s);
		s.executeBatch();
	}

}
