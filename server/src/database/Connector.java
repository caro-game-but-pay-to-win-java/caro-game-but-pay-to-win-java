package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import global.GVAR;

public class Connector {
	Connection connection = null;
	public Connector() {
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(GVAR.DB_CONNECTION_URL, GVAR.DB_USERNAME, GVAR.DB_PASSWORD);
			System.out.println("CONNECTOR ESTABLISHED!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public ResultSet query(String query) {
		try {
			Statement statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
