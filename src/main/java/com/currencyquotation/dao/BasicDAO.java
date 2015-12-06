package com.currencyquotation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BasicDAO {

	public static Connection getConnectionToDerby() {
		// -------------------------------------------
		// URL format is
		// jdbc:derby:<local directory to save data>
		// -------------------------------------------
		String dbUrl = "jdbc:derby:c:\\home\\DB\\Quotation;create=true";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void createDB() throws SQLException {
		Connection conn = BasicDAO.getConnectionToDerby();
		Statement stmt = conn.createStatement();

		StringBuilder createTable = new StringBuilder("CREATE TABLE quotation " )
			.append("( ")
			.append(" id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ")
		    .append(" quotation_date DATE NOT NULL, " )
		    .append(" currency VARCHAR(3) NOT NULL, ")
		    .append(" type CHAR(1) NOT NULL, ")
		    .append(" fee_buy DECIMAL(13,8) NOT NULL, ")
		    .append(" fee_sale DECIMAL(13,8) NOT NULL, ")
		    .append(" CONSTRAINT primary_key PRIMARY KEY (id) ) ");
		
		// create table
		stmt.executeUpdate(createTable.toString());
		
	}
	
	public static void dropTable() throws SQLException {
		Connection conn = BasicDAO.getConnectionToDerby();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("Drop Table quotation");
	}
	
	
	public static void closeObjects(ResultSet rs, Statement stmt, Connection conn) throws SQLException{
		if(rs != null) rs.close();
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
		
}
