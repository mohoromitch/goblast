import java.sql.*;

/**
 * Created by Frank on 2016-11-22.
 */
public class Test {
	public static final String HOST = "localhost",
		USERNAME = "mmohorov",
		PASSWORD = "pJazBRLEpHW{q2Cp2N",
		NAME = "orcl";
	public static final int PORT = 1521;

	public static void main(String[] argv) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;

		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {

			connection = DriverManager.getConnection(
				String.format("jdbc:oracle:thin:@%s:%d:%s", HOST, PORT, NAME), //"jdbc:oracle:thin:@localhost:1521:mkyong",
				USERNAME,
				PASSWORD);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			try {
				CallableStatement cs = connection.prepareCall("SELECT * FROM PLAYERS");
				cs.execute();
				ResultSet rs = cs.getResultSet();
				while(rs.next()) {
					System.out.println(rs.getString("FIRST_NAME"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}
