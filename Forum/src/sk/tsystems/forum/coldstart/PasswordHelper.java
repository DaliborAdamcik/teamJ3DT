package sk.tsystems.forum.coldstart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordHelper {

	public boolean passwordOverallControll(String password) {
		if (!charactersControll(password)) {
			System.out.println(
					"your password consists invalid character/s, choose only from A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~");
			return false;
		} else if (!lengthControll(password)) {
			System.out.println("your password must have at least 8 characters");
			return false;
		} else if (!digitControll(password)) {
			System.out.println("your password have to contain at least 1 digit");
			return false;
		} else if (!specialCharacterControll(password)) {
			System.out.println("your password have to contain at least 1 special character");
			return false;
		} else {
			return true;
		}
	}

	public boolean charactersControll(String password) {
		// Pattern pattern =
		// Pattern.compile("^[A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[\\]\\^\\_\\`{|}~]{1,}$");
		Pattern pattern = Pattern.compile("^[!-~]{1,}$");
		Matcher matcher = pattern.matcher(password);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public boolean lengthControll(String password) {
		// System.out.println("legtnh " + password.trim().length());
		if (password.trim().length() > 7) {
			return true;
		}
		return false;
	}

	public boolean digitControll(String password) {
		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				// System.out.println(password.charAt(i));
				return true;
			}
		}
		return false;
	}

	public boolean specialCharacterControll(String password) {
		for (int i = 0; i < password.length(); i++) {
			int ascii = (int) password.charAt(i);
			// System.out.println((int) password.charAt(i));
			if ((ascii > 32 && ascii < 48) || (ascii > 57 && ascii < 65) || (ascii > 90 && ascii < 97)
					|| (ascii > 122 && ascii < 127)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		PasswordHelper ps = new PasswordHelper();
		ps.passwordOverallControll("p!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~as^swo\\rd//<5");
	}

}
