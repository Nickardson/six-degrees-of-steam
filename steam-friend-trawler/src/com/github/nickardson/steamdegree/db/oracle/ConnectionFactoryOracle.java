package com.github.nickardson.steamdegree.db.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.JSONObject;

import com.github.nickardson.steamdegree.Settings;

public class ConnectionFactoryOracle {
	private static Connection connection = null;
	
	/**
	 * @return A brand new connection to the oracle database
	 */
	public static Connection getConnection() {
		// TODO: use connection pooling instead
		if (connection == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				// TODO: should these be queried every time?
				JSONObject settings = Settings.getConfigSettings();
				String url = settings.getString("dburl");
				String username = settings.getString("dbuser");
				String password = settings.getString("dbpass");
				
				try {
					connection = DriverManager.getConnection("jdbc:oracle:thin:@" + url, username, password);
				} catch (SQLException e) {
					System.out.println("Failed to created connection:");
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				System.err.println("Oracle driver not found.");
				e1.printStackTrace();
			}
		}
		
		return connection;
	}
}
