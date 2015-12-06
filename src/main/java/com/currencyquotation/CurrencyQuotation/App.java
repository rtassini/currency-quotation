package com.currencyquotation.CurrencyQuotation;

import java.math.BigDecimal;

import com.currencyquotation.controller.CurrencyQuotationController;

public class App {
	public static void main(String[] args) {
		String parameter = args[0];
		
		CurrencyQuotationController controller = new CurrencyQuotationController();
		if(parameter.equals("1")){
			controller.dropTable();
		}else if (parameter.equals("2")){
			controller.createDB();
		}else if (parameter.equals("3")){
			String fileName = args[1];
			controller.loadFile(fileName);
		}else{
			try {
				String from = args[1];
				String to = args[2];
				Number value = Double.parseDouble((args[3]));
				String date = args[4];
				BigDecimal convertedValue = controller.currencyQuotation(from, to, value, date);
				System.out.println(convertedValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
