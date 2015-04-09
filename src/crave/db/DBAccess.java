package crave.db;

import java.sql.*;
import java.util.Properties;

public class DBAccess {
	
	private String words = "null";
	private String type = "null";
	private String origin = "null";
	private String price = "null";
	private String cmp = "null";
	private String gb = "null";
	private String ob = "null";
	private String queryTemplate = "name " + words + " type " + type + " origin " + origin + " price " + price + " cmp " + cmp + " gb " + gb + " ob " + ob;
	
	/** The name of the MySQL account to use */
	private final String userName = "root";

	/** The password for the MySQL account */
	private final String password = "";

	/** The name of the MySQL server */
	private final String serverName = "localhost";

	/** The port of the MySQL server */
	private final int portNumber = 3306;

	/** The name of the database */
	private final String dbName = "restaurantsApp";
	
	/**
	 * Get a new database connection
	 * @return A connection to the database
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		return DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);
	}
	
	/**
	 * Queries the password associated with the given username in the database
	 * @param username The username who's password is being looked up
	 * @param conn The connection to the database
	 * @return A char array containing the password associated with the given username
	 */
	public char[] queryPassword(String username, Connection conn) {
		System.out.println("Validating login information...");
		Statement stmt = null;
		ResultSet rs = null;
		String pw = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select U.password from User U where U.name = " + username);
			while (rs.next()) {
				 pw = rs.getString("name");
			}
		} catch (SQLException e) {
			System.err.println("ERROR: Could not execute query");
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			System.out.print('\n');
		}
		return pw.toCharArray();
	}
	
	/** Executes a general query using the query generator and returns the result set */
	public ResultSet generalQuery(Connection conn) {
		System.out.println("Querying database...");
		Statement stmt = null;
		ResultSet rs = null;
		
		/* Get query from Andrews generator using querytemplate string */
		String query = "";	// = getQuery(queryTemplate);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("ERROR: Could not execute query");
			e.printStackTrace();
		} finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			System.out.print('\n');
		}
		return rs;
	}
	
	/**
	 * Connect to MySQL and execute queries specified in homework
	 */
	public void cleanup(Connection conn) {
		try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		System.out.println("Database access complete");
	}
}
