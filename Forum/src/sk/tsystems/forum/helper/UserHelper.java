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
	 * Password overall validator, check if password consists invalid character/s, password length and password strength
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 * @author Janka
	 */
	public static void passwordOverallControll(String password) throws PasswordCheckException {
		if (password == null)
			throw new PasswordCheckException("Password cannot be null");
		charactersControl(password);
		lengthControl(password);
		passwordStrengthControl(password);
		 digitControl(password);
		 specialCharacterControl(password);
	}

	/**
	 * Check if password consists invalid character/s
	 * 
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 */
	private static void charactersControl(String password) throws PasswordCheckException {
		Pattern pattern = Pattern.compile("^[!-~]{1,}$");
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			throw new PasswordCheckException(
					"your password consists invalid character/s, choose only from A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~");
		}
	}

	/**
	 * Check if password has at least 8 characters
	 * 
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 */
	private static void lengthControl(String password) throws PasswordCheckException {
		if (password.length() < 8)
			throw new PasswordCheckException("your password must have at least 8 characters");
	}

	/**
	 * Check password's strength
	 * 
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 */
	private static void passwordStrengthControl(String password) throws PasswordCheckException {
		if (passStrength(password) < 50) {
			throw new PasswordCheckException(
					"your password is not strong enough (try to use UPPERCASE and lowercase letters, numbers or special characters)");
		}
	}
	/**
	 * Check appearance of digit(s) in password 
	 * 
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 */
	private static void digitControl(String password) throws PasswordCheckException {
		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				return;
			}
		}
		throw new PasswordCheckException("your password have to contain at least 1 digit");
	}
	/**
	 * Check appearance of special character(s) in password 
	 * 
	 * @param password {@link String}
	 * @throws {@link PasswordCheckException}
	 */
	private static void specialCharacterControl(String password) throws PasswordCheckException {
		for (int i = 0; i < password.length(); i++) {
			int ascii = (int) password.charAt(i);
			if ((ascii > 32 && ascii < 48) || (ascii > 57 && ascii < 65) || (ascii > 90 && ascii < 97)
					|| (ascii > 122 && ascii < 127)) {
				return;
			}
		}
		throw new PasswordCheckException("your password have to contain at least 1 special character");
	} 


	/***
	 * nick name validator PART
	 * @param userName {@link String}
	 * @author Dalibor
	 * returns true if successful, throws exception otherwise
	 */
	public static boolean nickNameValidator(String userName) throws NickNameException {
		if (userName == null || userName.length() < 4)
			throw new NickNameException("Nickname must be 4 characters long");

		Pattern testValidNickName = Pattern.compile("^([a-z][a-z0-9]{3,})$");
		if (!testValidNickName.matcher(userName).matches())
			throw new NickNameException("Nickname can contain a-z and 0-9 characters. First character must be a-z.");

		return true;
	}

	/***
	 * String 2 Date convert valid format "dd.MM.yyyy HH:mm"
	 * @param dateString {@link String} date in String format
	 * @author Tuomas
	 */
	public static Date stringToDate(String dateString) throws BadDateException {

		return stringToDate(dateString, "dd.MM.yyyy");
	}

	/***
	 * String 2 Date convert valid format "dd.MM.yyyy HH:mm"
	 * @param dateString {@link String} date in String format
	 * @param dateFormat {@link String} date format
	 * @author Jano
	 * @see {@link Date} 
	 * @see {@link DateFormat} 
	 */
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
	 * Password strength check method returns password strength score. It takes
	 * account of password length, UPPERCASE and lowercase letters, numbers and
	 * special chars, count of consecutive letters and numbers.
	 * 
	 * Recommended minimal strength = 8 chars, score >= 50
	 * 
	 * @param password {@link String} 
	 * @return score in integer representation
	 * @author Tuomas
	 */
	public static int passStrength(String password) {
		int strength = 0;
		int uppers = 0, lowers = 0, numbers = 0, symbols = 0;
		int consecUppers = 0, consecLowers = 0, consecNumbers = 0;

		// COUNTER
		for (int a = 0; a < password.length(); a++) {
			char c = password.charAt(a);

			if (c >= 'a' && c <= 'z') {
				lowers++;
				if (a > 0) {
					if (password.charAt(a - 1) >= 'a' && password.charAt(a - 1) <= 'z') {
						consecLowers++;
					}
				}
			}

			if (c >= 'A' && c <= 'Z') {
				uppers++;
				if (a > 0) {
					if (password.charAt(a - 1) >= 'A' && password.charAt(a - 1) <= 'Z') {
						consecUppers++;
					}
				}
			}

			if (c >= '0' && c <= '9') {
				numbers++;
				if (a > 0) {
					if (password.charAt(a - 1) >= '0' && password.charAt(a - 1) <= '9') {
						consecNumbers++;
					}
				}
			}

			if ((c >= 32 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 126))
				symbols++;
		}

		// ADDITIONS

		// password length rate: (n*4)
		strength += password.length() * 4;
		// lowercase LETTERS rate: ((len-n)*2)
		if (lowers > 0) {
			strength += (password.length() - lowers) * 2;
		}
		// UPPERCASE letters rate: ((len-n)*2)
		if (uppers > 0) {
			strength += (password.length() - uppers) * 2;
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
