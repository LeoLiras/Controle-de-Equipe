package program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	static SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date stringToDate(String data) {
		try {
			return Utils.date_format.parse(data);
		}catch(ParseException e){
			return null;
		}
	}
}
