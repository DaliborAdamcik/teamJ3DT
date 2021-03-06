package sk.tsystems.forum.helper;

import java.util.Date;
import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class TestHelper {

	// TODO implement grenerating of special characters (@/* an so..)
	/**
	 * Defines types of letters
	 * 
	 * @author Dalibor
	 */
	public enum CharType {
		UPPERCASELETTER, LOWERCASELETTER, NUMERIC
	};

	public static final byte NUMBER_OF_LETTERS = 'Z' - 'A' + 1;
	public static final byte NUMBER_OF_NUMERICS = 10;

	/**
	 * List of entities to be deleted from database after tests end;
	 */

	// public static List<Object> listOfTemporaryObjects = new
	// ArrayList<Object>();

	// /**
	// * Adds an object to list of temporary list
	// * @param temporary object
	// * @return true
	// */
	// public static boolean addToListOfTemporaryObjects(Object
	// temporaryObject){
	// listOfTemporaryObjects.add(temporaryObject);
	// return true;
	// }
	/**
	 * Removes all temporary objects from the database
	 * 
	 * @return true if successful, false otherwise
	 */
	public static boolean removeTemporaryObjects(List<Object> listOfTemporaryObjects) {
		try(JpaConnector jpa = new JpaConnector())
		{
			for (Object object : listOfTemporaryObjects) {
				if(!(object instanceof CommonEntity))
					continue;
				jpa.remove(object);
			}
		}
		// listOfTemporaryObjects.clear();
		return true; // TODO RZYAAAA
	}

	/**
	 * Generates one random character of specified type
	 * 
	 * @param charType
	 * @return
	 * @author Dalibor
	 */
	public static char RandomChar(CharType charType) {
		Random rand = new Random();
		switch (charType) {
		case LOWERCASELETTER:
			return (char) (rand.nextInt(NUMBER_OF_LETTERS) + 'a');
		case NUMERIC:
			return (char) (rand.nextInt(NUMBER_OF_NUMERICS) + '0');
		case UPPERCASELETTER:
			return (char) (rand.nextInt(NUMBER_OF_LETTERS) + 'A');
		default:
			throw new RuntimeException("randomChar - An unimplemented option selected: " + charType.toString());
		}
	}

	/**
	 * Generates an random string with specified number of chars (letters and
	 * numerics) Position of individual characters is random
	 * 
	 * @param letterCount
	 *            Count of letters([a-z, A-Z]) in result string
	 * @param numericCount
	 *            Count of numeric characters in result
	 * @return Random string
	 * @author Dalibor
	 */
	public static String randomString(int letterCount, int numericCount) // TODO
																			// add
																			// special
																			// characters
	{
		if (letterCount < 0 || numericCount < 0 || letterCount + numericCount == 0) {
			throw new RuntimeException(String.format("Invalid configuration for randomString: letters %d / numerics %d",
					letterCount, numericCount));
		}

		StringBuilder strBuild = new StringBuilder();
		Random rand = new Random();

		do {
			if (rand.nextBoolean()) {
				if (numericCount-- <= 0)
					continue;
				strBuild.append(RandomChar(CharType.NUMERIC));
			} else // is letter
			{
				if (letterCount-- <= 0)
					continue;

				strBuild.append(RandomChar(rand.nextBoolean() ? CharType.LOWERCASELETTER : CharType.UPPERCASELETTER));
			}
		} while (letterCount > 0 || numericCount > 0);

		return strBuild.toString();
	}

	/**
	 * Generates an random string (letters and numerics) with specified length
	 * Position of individual characters is random
	 * 
	 * @param letterNumericBound
	 * @return
	 * @author Dalibor
	 */
	public static String randomString(int length) {
		if (length <= 0)
			throw new RuntimeException("randomString - invalid length: " + length);

		Random rand = new Random();
		int numc = rand.nextInt(length);
		return randomString(length - numc, numc);
	}

	public static <T> T nonParaConstructor(Class<T> clazz) {
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			@SuppressWarnings("unchecked")
			T inst = (T) constructor.newInstance();
			return inst;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates random date since 2001 to now
	 * @author Dalik
	 * @return random date limited by today
	 */
	public static Date randomDate() {
		Random rand = new Random();
		long maxTimeStamp = (new Date()).getTime();
		long timestamp;
		int i = 0;
		do{
			timestamp = rand.nextLong();
			timestamp = (timestamp<0?timestamp*-1:timestamp)+1;
			timestamp &= maxTimeStamp;
			if(i++ >1000)
				throw new RuntimeException("Failed to generate random date");
		} while(timestamp >= maxTimeStamp);
		
		return new Date(timestamp);
	}

}
