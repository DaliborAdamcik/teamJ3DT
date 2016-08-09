package sk.tsystems.forum.coldstart;

import java.util.Date;

public class String2DateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(stringToDate("31.12.9999 23:59:59"));
		// System.out.println(stringToDate("01.2.1970 0:1:9"));
		
		System.out.println(stringToDate("31.12.2000 23:59:59").toString());
		
	}

	public static Date stringToDate(String dateString) {
		String buffer = "";
		String datePattern = "";
		int step = 1;

		// IS STRING LENGTH VALID ?
		if (dateString.length() < 4) {
			return null;
		}

		for (int a = 0; a < dateString.length(); a++) {
			char c = dateString.charAt(a);

			// IS DIGIT ?
			if (c >= '0' && c <= '9') {
				buffer = buffer + c;
			}

			// IS CHAR ? or IS LAST CHAR ?
			if (c == ' ' || c == '.' || c == '-' || c == ':' || c == '/' || a == dateString.length() - 1) {
				// if step != year
				if (step != 3) {
					// if buffer is 1 char long
					if (buffer.length() == 1) {
						buffer = "0" + buffer;
					}

					if (buffer.length() == 2) {
						datePattern = datePattern + buffer;
						buffer = "";
						step++;
					} else {
						return null;
					}

				} else { // if step == year
					if (buffer.length() == 4) {
						datePattern = datePattern + buffer;
						buffer = "";
						step++;
					} else {
						return null;
					}
				}
			}
		}

		return patternToDate(datePattern);
	}

	private static Date patternToDate(String pattern) {
		Date date = new Date();
		StringBuilder stringBuilder = new StringBuilder();
		int a = 0;
		String buffer = "";

		// DAY
		buffer = stringBuilder.append(pattern.charAt(0)).append(pattern.charAt(1)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 1 && a <= 31) {
			date.setDate(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}

		// MONTH
		buffer = stringBuilder.append(pattern.charAt(2)).append(pattern.charAt(3)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 1 && a <= 12) {
			date.setMonth(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}

		// YEAR
		buffer = stringBuilder.append(pattern.charAt(4)).append(pattern.charAt(5)).append(pattern.charAt(6))
				.append(pattern.charAt(7)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 1970 && a <= 2032) {
			date.setYear(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}

		// HOUR
		buffer = stringBuilder.append(pattern.charAt(8)).append(pattern.charAt(9)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 0 && a <= 23) {
			date.setHours(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}

		// MINUTE
		buffer = stringBuilder.append(pattern.charAt(10)).append(pattern.charAt(11)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 0 && a <= 59) {
			date.setMinutes(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}

		// SECOND
		buffer = stringBuilder.append(pattern.charAt(12)).append(pattern.charAt(13)).toString();
		a = Integer.parseInt(buffer);
		if (a >= 0 && a <= 59) {
			date.setSeconds(a);
			stringBuilder.delete(0, stringBuilder.length());
		} else {
			return null;
		}
		
		return date;
	}

}
