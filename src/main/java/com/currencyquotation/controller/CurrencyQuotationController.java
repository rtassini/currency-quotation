package com.currencyquotation.controller;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.currencyquotation.dao.BasicDAO;
import com.currencyquotation.dao.CurrencyQuotationDAO;

public class CurrencyQuotationController {

	public BigDecimal currencyQuotation(String from, String to, Number value, String quotation)throws Exception{
		
		BigDecimal convertedValue = null;
		
		CurrencyQuotationDAO dao = new CurrencyQuotationDAO();
		
		dao.validateParameter(from);
		dao.validateParameter(to);
		
		BigDecimal valueToConvert = new BigDecimal(value.toString());
		validateValue(valueToConvert);
		
		Date quotationDate = validateDate(quotation);
		BigDecimal brazilianValue = convertToBrazilianCurrency(from, valueToConvert, quotationDate);
		BigDecimal quotationValue = dao.getQuotation(quotationDate, to);
		
		convertedValue = brazilianValue.divide(quotationValue, 2, RoundingMode.HALF_DOWN);
			
		return convertedValue;
	}
	
	public BigDecimal convertToBrazilianCurrency(String from, BigDecimal value, Date quotationDate) throws Exception{
		CurrencyQuotationDAO dao = new CurrencyQuotationDAO();
		BigDecimal quotationValue = dao.getQuotation(quotationDate, from);
		
		return value.multiply(quotationValue);
	}

	
	public Date validateDate(String quotationDate) throws ParseException{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(quotationDate));
		
		// se for domingo
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DATE, -2);
            
        // se for sábado
        else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
           calendar.add(Calendar.DATE, -1);
            
        return calendar.getTime();
		
	}
	
	public void validateValue(BigDecimal value) throws Exception{
		if(value.compareTo(BigDecimal.ZERO) < 1)
			throw new Exception("O valor deve ser maior do que zero");
	}
	
	public void dropTable(){
		try {
			BasicDAO.dropTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createDB(){
		try {
			BasicDAO.createDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void loadFile(){
		Scanner scanner = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			scanner = new Scanner(new FileReader("\\home\\file\\20151204.csv"))
					.useDelimiter(";|\\n");
		
			while (scanner.hasNext()) {
				String data = scanner.next();
				scanner.next();
				scanner.next();
				String currency = scanner.next();
				BigDecimal feeBuy = scanner.nextBigDecimal();
				BigDecimal feeSale = scanner.nextBigDecimal();
				scanner.next();
				scanner.next();
				
				CurrencyQuotationDAO dao = new CurrencyQuotationDAO();
				dao.insert(df.parse(data), currency, feeBuy, feeSale);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
