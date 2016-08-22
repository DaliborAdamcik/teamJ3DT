package sk.tsystems.forum.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.forum.entity.exceptions.field.user.BadDateException;
import sk.tsystems.forum.entity.exceptions.field.user.NickNameException;
import sk.tsystems.forum.entity.exceptions.field.user.PasswordCheckException;

public class UserHelper {

	/***
	 * password validator part
	 * 
	 * @author Janka
	 */
	public static void passwordOverallControll(String password) throws PasswordCheckException {
		if (password == null)
			throw new PasswordCheckException("Password cannot be null");
		charactersControll(password);
		lengthControll(password);
		digitControll(password);
		specialCharacterControll(password);
	}

	private static void charactersControll(String password) throws PasswordCheckException {
		Pattern pattern = Pattern.compile("^[!-~]{1,}$");
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			throw new PasswordCheckException(
					"your password consists invalid character/s, choose only from A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~");
		}
	}

	private static void lengthControll(String password) throws PasswordCheckException {
		if (password.length() < 8)
			throw new PasswordCheckException("your password must have at least 8 characters");
	}

	private static void digitControll(String password) throws PasswordCheckException {
		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				return;
			}
		}
		throw new PasswordCheckException("your password have to contain at least 1 digit");
	}

	private static void specialCharacterControll(String password) throws PasswordCheckException {
		for (int i = 0; i < password.length(); i++) {
			int ascii = (int) password.charAt(i);
			if ((ascii > 32 && ascii < 48) || (ascii > 57 && ascii < 65) || (ascii > 90 && ascii < 97)
					|| (ascii > 122 && ascii < 127)) {
				return;
			}
		}
		throw new PasswordCheckException("your password have to contain at least 1 special character");
	}

	public static void main(String[] args) {
		/*
		try {
			// passwordOverallControll("pas");
			passwordOverallControll("12345678");
			System.out.println("ok");
		} catch (PasswordCheckException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		*/

		System.out.printf("a:\t%s\n", passStrength("a"));
		System.out.printf("1:\t%s\n", passStrength("1"));
		System.out.printf("Aa:\t%s\n", passStrength("Aa"));
		System.out.printf("Aa1:\t%s\n", passStrength("Aa1"));
		System.out.printf("A1a:\t%s\n", passStrength("A1a"));
		System.out.printf("A1a.:\t%s\n", passStrength("A1a."));
		System.out.printf("abcde:\t%s\n", passStrength("abcde"));
		System.out.printf("abcdE:\t%s\n", passStrength("abcdE"));
		System.out.printf("abCde:\t%s\n", passStrength("abCde"));
		System.out.printf("12345:\t%s\n", passStrength("12345"));
		System.out.printf("Aa.7Aa7:\t%s\n", passStrength("Aa.7Aa7"));
		
		System.out.println();
		
		System.out.printf("123456.a:\t%s\n", passStrength("123456.a"));
		System.out.printf("123456.7:\t%s\n", passStrength("123456.7"));
		System.out.printf("Mypass.1:\t%s\n", passStrength("123456.a"));
		System.out.printf("IdE.4lN.e He5.l0:\t%s\n", passStrength("IdE.4lN.e He5.l0"));
		
	}

	/***
	 * nick name validator PART
	 * 
	 * @author Dalibor
	 */
	public static boolean nickNameValidator(String userName) throws NickNameException {
		if (userName==null || userName.length() < 4)
			throw new NickNameException("Nickname must be 4 characters long");

		Pattern testValidNickName = Pattern.compile("^([a-z][a-z0-9]{3,})$");
		if (!testValidNickName.matcher(userName).matches())
			throw new NickNameException("Nickname can contain a-z and 0-9 characters. First character must be a-z.");

		return true;
	}
	
	/***
	 * String 2 Date convert
	 * valid format "dd.MM.yyyy HH:mm"
	 * 
	 * @author Tuomas
	 */
	public static Date stringToDate(String dateString) throws BadDateException {
	
		return stringToDate(dateString,"dd.MM.yyyy");
	}
	
	public static Date stringToDate(String dateString, String dateFormat) throws BadDateException {
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		Date date;

		try {
			date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			throw new BadDateException("Date in invalid format. Valid format is \"dd.MM.yyyy HH:mm\".", e);
		}
	}

	/***
	 * Password strength calc.
	 * 
	 * Recomended minimal strength = 8 chars, score >= 50
	 * 
	 * @author Tuomas
	 */
	public static int passStrength(String pass) {
		int strength = 0;
		int uppers = 0, lowers = 0, numbers = 0, symbols = 0;
		int consecUppers = 0, consecLowers = 0, consecNumbers = 0;

		// COUNTER
		for (int a = 0; a < pass.length(); a++) {
			char c = pass.charAt(a);

			if (c >= 'a' && c <= 'z') {
				lowers++;
				if (a > 0) {
					if (pass.charAt(a - 1) >= 'a' && pass.charAt(a - 1) <= 'z') {
						consecLowers++;
					}
				}
			}

			if (c >= 'A' && c <= 'Z') {
				uppers++;
				if (a > 0) {
					if (pass.charAt(a - 1) >= 'A' && pass.charAt(a - 1) <= 'Z') {
						consecUppers++;
					}
				}
			}

			if (c >= '0' && c <= '9') {
				numbers++;
				if (a > 0) {
					if (pass.charAt(a - 1) >= '0' && pass.charAt(a - 1) <= '9') {
						consecNumbers++;
					}
				}
			}

			if ((c >= 32 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 126))
				symbols++;
		}

		// ADDITIONS

		// password length rate: (n*4)
		strength += pass.length() * 4;
		// lowercase LETTERS rate: ((len-n)*2)
		if (lowers > 0) {
			strength += (pass.length() - lowers) * 2;
		}
		// UPPERCASE letters rate: ((len-n)*2)
		if (uppers > 0) {
			strength += (pass.length() - uppers) * 2;
		}
		// numbers rate: (n*4)
		if (numbers > 0) {
			strength += numbers * 4;
		}
		// symbols rate: (n*6)
		if (symbols > 0) {
			strength += symbols * 6;
		}

		// DEDUCTIONS

		// only letters rate: -n
		if (numbers + symbols == 0) {
			strength -= (lowers + uppers);
		}
		// only numbers rate: -(n*2)
		if (lowers + uppers + symbols == 0) {
			strength -= (numbers * 2);
		}
		// consecutive uppercase letters rate: -(n*2)
		strength -= (consecUppers * 2);
		// consecutive lowercase letters rate: -(n*2)
		strength -= (consecLowers * 2);
		// consecutive numbers rate: -(n*3)
		strength -= (consecNumbers * 3);

		return strength;
	}
	
}
