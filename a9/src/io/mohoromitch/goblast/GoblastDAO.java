package io.mohoromitch.goblast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Dao with prepared statements to isolate sql logic.
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class GoblastDAO {

	private static GoblastDAO instance = null;

	/**
	 * Returns the current process's instance of the dao.
	 * @return The current shared dao.
	 * @throws Exception Any exception that occurs should there be an instantiation of the dao.
	 */
	public static synchronized GoblastDAO getSharedInstance() throws Exception {
		if (instance == null)
			instance = new GoblastDAO(new Database());
		return instance;
	}

	Database database;

	/**
	 * Creates a dao for a given database interface.
	 * @param database Database interface.
	 */
	private GoblastDAO(Database database) {
		this.database = database;
	}

	/**
	 * Queries for the list of tables currently in the database.
	 * @return A ResultSet for a query returning table names
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public ResultSet getTableList() throws SQLException {
		return database.execute("SELECT table_name FROM user_tables");
	}

	/**
	 * Queries fot he list of views currently in the database.
	 * @return A ResultSet for the query of views.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public ResultSet getViewList() throws SQLException {
		return database.execute("SELECT view_name FROM user_views");
	}

	/**
	 * Qeries for the complete table of data of a specific table.
	 * @param table Table name to query.
	 * @return A ResultSet containing all of the table's data.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public ResultSet getAllTableContentsOf(String table) throws SQLException {
		String query = "SELECT * FROM "+table;
		PreparedStatement ps = database.getPreparedStatementFrom(query);
		return ps.executeQuery();
	}

	/**
	 * Creates all tables.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public void createAllTables() throws SQLException {
		database.executeBatch(Queries.C_TABLES);
	}

	/**
	 * Drops all tables.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public void dropAllTables() throws SQLException {
		database.executeBatch(Queries.D_TABLES);
	}

	/**
	 * Populates all tables.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public void populateTables() throws SQLException {
		database.executeBatch(Queries.P_TABLES);
	}

	/**
	 * Creates all views.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public void createAllViews() throws SQLException {
		database.executeBatch(Queries.C_VIEWS);
	}

	/**
	 * Drops all views.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public void dropAllViews() throws SQLException {
		database.executeBatch(Queries.D_VIEWS);
	}

	/**
	 * Executes a raw query.
	 * @param query Sql to execute.
	 * @return A resultset of the query output.
	 * @throws SQLException Any exception that occurred during the execution of the sql.
	 */
	public ResultSet executeRaw(String query) throws SQLException {
		return database.execute(query);
	}

}
