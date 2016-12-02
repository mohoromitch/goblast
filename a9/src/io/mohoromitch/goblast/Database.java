package io.mohoromitch.goblast;
import java.sql.*;
import java.util.Properties;

/**
 * A database interface. For isolating database transactions.
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class Database {

	public static final String HOST = "localhost",
		USERNAME = "mmohorov",
		PASSWORD = "pJazBRLEpHW{q2Cp2N",
		NAME = "orcl";
	public static final int PORT = 1521;

	private static final NotificationManager NM = NotificationManager.getSharedInstance();

	private Connection connection;


	/**
	 * Connects to the database, and initilizes a DAO instance.
	 * @throws Exception
	 */
	public Database() throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			throw e;
		}
		try {
			Properties prop = new Properties();
			prop.put("user", USERNAME);
			prop.put("password", PASSWORD);
			prop.put("allowMultiQueries", true);
			connection = DriverManager.getConnection(
				String.format("jdbc:oracle:thin:@%s:%d:%s", HOST, PORT, NAME),
				prop);
		} catch (SQLException e) {
			System.out.println("Database connection failed.");
			throw e;
		}
		if(connection == null) {
			throw new Exception("Failed to make connection");
		}
	}

	/**
	 * Executes a simple Sql query.
	 * @param query Sql query to execute.
	 * @return A ResultSet of the data returned.
	 * @throws SQLException An exception if the Sql caused an error.
	 */
	public ResultSet execute(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	/**
	 * Executes a multi-query batch statment. Does so by executing each statement in its own database call.
	 * @param query Sql multi query.
	 */
	public void executeBatch(String query) {
		for (String q : query.split(";")) { // Splits the queries
			q.replace("\n", " ");
			String trimmed = q.trim();

			// If valid, execute query
			if (!trimmed.isEmpty()) {
				ResultSet rs = null;
				try {
					rs = execute(trimmed);
					if (rs != null) rs.close();
				} catch (Exception e) {
					// If an exception occured, send the error notification
					NM.notify(NotificationManager.NOTIFICATION_SQL_ERROR, e);
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Prepares an sql statment for use as a prepared statement.
	 * @param query Query to prepare.
	 * @return The PreparedStatement object.
	 * @throws SQLException Any exception that occurred preparing the statment.
	 */
	public PreparedStatement getPreparedStatementFrom (String query) throws SQLException {
		return connection.prepareStatement(query);
	}

}
