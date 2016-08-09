package sk.tsystems.forum.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sk.tsystems.forum.helper.NickNameException;

public class UserHelper {

	/***
	 * password validator part
	 * @author Janka
	 */
	public boolean passwordOverallControll(String password) throws PasswordCheckException {
		if(password == null)
			throw new PasswordCheckException("Password cannot be null");
			
		if (!charactersControll(password)) 
			throw new PasswordCheckException("your password consists invalid character/s, choose only from A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~");
		
		if (!lengthControll(password)) 
			throw new PasswordCheckException("your password must have at least 8 characters");

		if (!digitControll(password)) 
			throw new PasswordCheckException("your password have to contain at least 1 digit");

		if (!specialCharacterControll(password)) 
				throw new PasswordCheckException("your password have to contain at least 1 special character");

		return true;
	}

	private boolean charactersControll(String password) {
		// Pattern pattern =
		// Pattern.compile("^[A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@[\\]\\^\\_\\`{|}~]{1,}$");
		Pattern pattern = Pattern.compile("^[!-~]{1,}$");
		Matcher matcher = pattern.matcher(password);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	private boolean lengthControll(String password) {
		// System.out.println("legtnh " + password.trim().length());
		if (password.trim().length() > 7) {
			return true;
		}
		return false;
	}

	private boolean digitControll(String password) {
		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				// System.out.println(password.charAt(i));
				return true;
			}
		}
		return false;
	}

	private boolean specialCharacterControll(String password) {
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
		UserHelper ps = new UserHelper();
		try {
			ps.passwordOverallControll(null);
			ps.passwordOverallControll("p!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~as^swo\\rd//<5");
			ps.passwordOverallControll("p!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~as^swo\\rd//<5");
			System.out.println("ok");
		} catch (PasswordCheckException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/***
	 * nick name validator PART
	 * @author Dalibor
	 */
	public static boolean nickNameValidator(String userName) throws NickNameException
	{
    	if(userName.length()<4)
    		throw new NickNameException("Nickname must be 4 characters long");
    	
    	Pattern testValidNickName = Pattern.compile("^([a-z][a-z0-9]{3,})$");
    	if(!testValidNickName.matcher(userName).matches())
    		throw new NickNameException("Nickname can contain a-z and 0-9 characters. First character muste be a-z.");
    	
    	return true;
	}
}
