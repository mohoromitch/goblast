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

	public ResultSet executeRaw(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	public ResultSet execute(String query) throws SQLException {
		ResultSet rs;
		try {
			CallableStatement cs = connection.prepareCall(query);
			cs.execute();
			rs = cs.getResultSet();
		} catch (SQLException e) {
			throw e;
		}
		return rs;
	}

	public void executeBatch(String query) throws SQLException {
		for (String q : query.split(";")) {
			q.replace("\n", " ");
			q.replace(",", ", ");
			String trimmed = q.trim();
			if (!trimmed.isEmpty()) {
				execute(trimmed).close();
			}
		}
	}

	public PreparedStatement getPreparedStatementFrom (String query) throws SQLException {
		return connection.prepareStatement(query);
	}

	public Statement createStatement() throws SQLException {
		return connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	}


}
