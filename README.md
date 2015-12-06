## currency-quotation ##
Project constructed to calculate the current quotation from one to other

# Where:
- from: String with the currency name (example "USD") you want to convert;
- to: String with the currency name (example "EUR") you want to see the result;
- value: The value that should be converted. The currency of this value will be expressed in the
“from” parameter;
- quotation: A date as String in the format “dd/MM/yyyy”;
 
# Restrictions:
- You shall not work with non-native classes / libraries;
- If the from or to parameters are not valid, an exception must be thrown;
- If the value is smaller than zero, an exception must be thrown;
- For non-working days (Saturday and Sunday, ignoring holidays) takes the quotation from the
immediately preceding business day. If the quotation of the previous day is not available, an
exception must be thrown;
- If the quotation date is not available, an exception must be thrown;
- The data source used will be the Brazilian central bank CSV file available at:
http://www4.bcb.gov.br/pec/taxas/batch/cotacaomoedas.asp?id=txtodas
- The return value should be rounded to two decimal places.
- You must convert the currency through rate "Taxa Compra".


## Tecnologies ##
- Java 8
- Maven
- BD Derby
- IDE Eclipse Luna

## Instruções para execução ##

O projeto pode ser importado no Eclipse e executado através e ao menos a primeira execução deve ser através do comando mvn clean install.

Para efeitos de teste, pode-se executar diretamente a classe App, passando alguns parâmetros, conforme abaixo:

- Primeiro parâmetro, numérico de 1 a 4, sendo:
 
	1. Drop table
	2. Create DB, deve ser rodado na primeira execução
	3. Load file, quando se quiser carregar um arquivo, que deve estar no diretório  \\home\\file.
	4. Executa o cálculo da cotação, com a seguinte lógica, primeiro transforma em real e em seguida para outra moeda.
	
- Demais parâmetros para o caso da opção 3, deve ser o nome do arquivo, ficando da seguinte forma:
	- 3 20151204.csv

- Demais parâmetros para o caso da opção 4, devem ficar da seguinte forma:
	- 4 EUR USD 1000 05/12/2015

	

  

