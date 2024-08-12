package com.project.gym.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database implements DAO {
	
	//Fields pertaining to the JDBC API (Connection, PreparedStatement)
		//ResultSet
		private Connection connection;
		
		//Static here does not allow to create an instance that belongs to an object, but create 
		//the fields that belongs to the entire class  itself. 
		//So every time we are referencing to one single database.  
		
		private static Database db = new Database();
		
		private Database() {
			// Private constructor to enforce singleton pattern
		}
		public static Database getInstance() {
			return db;
		}
	
	@Override
	public Connection getConnection() {
	
		if (connection == null || isClosed(connection)) {
				// Re-establish the connection if it's null or closed
				connect();
			}
			return connection;
		}
		
		
	
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int executeUpdate(PreparedStatement preparedStatement) {
		int result = 0;
		try {
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public ResultSet executeQuery(PreparedStatement preparedStatement) {
		ResultSet result = null;
		try {
			result = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "riwajrai";
			String password = "password1";
			String url = "jdbc:mysql://localhost:3307/gymjdbc";
			
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isClosed(Connection connection) {
		try {
			return connection == null || connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	
}
