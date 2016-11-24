package io.mohoromitch.goblast;
import java.sql.*;
import java.text.CollationElementIterator;

/**
 * Created by mitchellmohorovich on 2016-11-24.
 */
public class Database {
	public static final String HOST = "localhost",
		USERNAME = "mmohorov",
		PASSWORD = "pJazBRLEpHW{q2Cp2N",
		NAME = "orcl";
	public static final int PORT = 1521;

	private Connection connection;


	public Database() throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			throw e;
		}
		try {
			connection = DriverManager.getConnection(
				String.format("jdbc:oracle:thin:@%s:%d:%s", HOST, PORT, NAME), //"jdbc:oracle:thin:@localhost:1521:mkyong",
				USERNAME,
				PASSWORD);
		} catch (SQLException e) {
			System.out.println("Database connection failed.");
			throw e;
		}
		if(connection == null) {
			throw new Exception("Failed to make connection");
		}
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

}
