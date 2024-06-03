package global;

import java.time.format.DateTimeFormatter;

public class GVAR {
	public static DateTimeFormatter DTFormatter = DateTimeFormatter.ISO_TIME; 
	
	public static int SERVER_PORT = 9000;
	public static int MATRIX_SIZE = 19;
	
	public static String DB_USERNAME = "root";
	public static String DB_PASSWORD = "";
	public static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/ptwcarogame";
}