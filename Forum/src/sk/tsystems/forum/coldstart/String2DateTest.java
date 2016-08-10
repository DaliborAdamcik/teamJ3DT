package sk.tsystems.forum.coldstart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class String2DateTest {

	public static void main(String[] args) {
		System.out.println(stringToDate("25.10.1999 16:48:59").toString());
		System.out.println(stringToDate("25.10.1999 16:00:00").toString());
	}
	
	public static Date stringToDate(String dateString) {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

}
