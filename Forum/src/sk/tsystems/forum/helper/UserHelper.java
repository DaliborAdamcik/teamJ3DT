package sk.tsystems.forum.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sk.tsystems.forum.helper.NickNameException;

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
		try {
			//passwordOverallControll("pas");
			passwordOverallControll("12345678");
			System.out.println("ok");
		} catch (PasswordCheckException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	/***
	 * nick name validator PART
	 * 
	 * @author Dalibor
	 */
	public static boolean nickNameValidator(String userName) throws NickNameException {
		if (userName.length() < 4)
			throw new NickNameException("Nickname must be 4 characters long");

		Pattern testValidNickName = Pattern.compile("^([a-z][a-z0-9]{3,})$");
		if (!testValidNickName.matcher(userName).matches())
			throw new NickNameException("Nickname can contain a-z and 0-9 characters. First character must be a-z.");

		return true;
	}
}
