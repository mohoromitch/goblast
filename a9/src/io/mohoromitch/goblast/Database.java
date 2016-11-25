package io.mohoromitch.goblast;
import java.sql.*;
import java.util.Properties;

/**
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class Database {

	public static int POOL_SIZE = 10;
	public static final String HOST = "localhost",
		USERNAME = "mmohorov",
		PASSWORD = "pJazBRLEpHW{q2Cp2N",
		NAME = "orcl";
	public static final int PORT = 1521;

	private static final NotificationManager NM = NotificationManager.getSharedInstance();

	final Thread MAIN_THREAD = Thread.currentThread();
	private Connection connection;


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

	public ResultSet execute(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	public void executeBatch(String query) {
		for (String q : query.split(";")) {
			q.replace("\n", " ");
			String trimmed = q.trim();
			if (!trimmed.isEmpty()) {
				ResultSet rs = null;
				try {
					rs = execute(trimmed);
					if (rs != null) rs.close();
				} catch (Exception e) {
					NM.notify(NotificationManager.NOTIFICATION_SQL_ERROR, e);
					e.printStackTrace();
				}
			}
		}

	}

	public PreparedStatement getPreparedStatementFrom (String query) throws SQLException {
		return connection.prepareStatement(query);
	}

}
