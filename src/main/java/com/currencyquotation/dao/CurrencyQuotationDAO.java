package com.currencyquotation.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CurrencyQuotationDAO {

	public void insert(Date quotationDate, String currency, BigDecimal feeBuy, BigDecimal feeSale) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			String query = "insert into quotation (quotation_date, currency, fee_buy, fee_sale) values (?,?,?,?)";

			conn = BasicDAO.getConnectionToDerby();

			stmt = conn.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(quotationDate.getTime()));
			stmt.setString(2, currency);
			stmt.setBigDecimal(3, feeBuy);
			stmt.setBigDecimal(4, feeSale);

			stmt.executeUpdate();

		} finally{
			BasicDAO.closeObjects(null, stmt, conn);
		}

	}
	
	
	public BigDecimal getQuotation(Date quotationDate, String currency) throws Exception{
		BigDecimal feeBuy = null;
		StringBuilder query = new StringBuilder(" SELECT fee_buy FROM quotation ")
			.append(" WHERE quotation_date = ?")
			.append(" AND currency = ?");
		
		
		Connection conn = BasicDAO.getConnectionToDerby();

		PreparedStatement stmt = conn.prepareStatement(query.toString());
		stmt.setDate(1, new java.sql.Date(quotationDate.getTime()));
		stmt.setString(2, currency);
		
		ResultSet rs = null;
		try{
			rs = stmt.executeQuery();
			
			boolean noData = true; 
			
			while (rs.next()){
				feeBuy = rs.getBigDecimal(1);
				noData = false;
			}
			
			if (noData)
				throw new Exception("Don't exist quotation to the parameters passed");
		
		}finally{
			BasicDAO.closeObjects(rs, stmt, conn);
		}
		return feeBuy;
	}
	
	
	public void validateParameter(String parameter) throws Exception{
		StringBuilder query = new StringBuilder(" SELECT * FROM quotation ")
		.append(" WHERE currency = ?");
		
		Connection conn = BasicDAO.getConnectionToDerby();

		PreparedStatement stmt = conn.prepareStatement(query.toString());
		stmt.setString(1, parameter);
		
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
	
			if (!rs.next())
				throw new Exception("Invalid parameter: " + parameter );
		
		}finally{
			BasicDAO.closeObjects(rs, stmt, conn);
		}	
		
	}
	
}
