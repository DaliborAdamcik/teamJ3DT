package sk.tsystems.forum.junittest;

import java.util.Random;

public class TestHelper {
	
	// TODO implement grenerating of special characters (@/* an so..)
	/**
	 * Defines types of letters	 
	 * @author Dalibor
	 */
	public enum CharType {UPPERCASELETTER, LOWERCASELETTER, NUMERIC};
	public static final byte NUMBER_OF_LETTERS = 'Z'-'A'; 
	public static final byte NUMBER_OF_NUMERICS = 10; 
	
	/**
	 * Generates one random character of specified type
	 * @param charType
	 * @return
	 * @author Dalibor
	 */
	public static char RandomChar(CharType charType)
	{
		Random rand = new Random();
		switch(charType)
		{
			case LOWERCASELETTER: return (char)(rand.nextInt(NUMBER_OF_LETTERS)+'a');
			case NUMERIC: return (char)(rand.nextInt(NUMBER_OF_LETTERS+1)+'0');
			case UPPERCASELETTER: return (char)(rand.nextInt(NUMBER_OF_LETTERS)+'A');
			default: throw new RuntimeException("randomChar - An unimplemented option selected: "+charType.toString()); 
		}
	}

	/**
	 * Generates an random string with specified number of chars (letters and numerics)
	 * Position of individual characters is random 
	 * @param letterCount Count of letters([a-z, A-Z]) in result string
	 * @param numericCount Count of numeric characters in result
	 * @return Random string
	 * @author Dalibor
	 */
	public static String randomString(int letterCount, int numericCount) // TODO add special characters
	{
		if(letterCount <0 || numericCount <0 || letterCount+numericCount==0)
		{
			throw new RuntimeException(
					String.format("Invalid configuration for randomString: letters %d / numerics %d", letterCount, numericCount));
		}
		
		StringBuilder strBuild = new StringBuilder();
		Random rand = new Random();
		
		do {
			if(rand.nextBoolean())
			{
				if(numericCount-- <= 0)
					continue;
				strBuild.append(RandomChar(CharType.NUMERIC));
			}
			else // is letter
			{
				if(letterCount-- <= 0)
					continue;

				strBuild.append(RandomChar(rand.nextBoolean()?CharType.LOWERCASELETTER:CharType.UPPERCASELETTER));
			}
		}
		while (letterCount>0 || numericCount>0);
		
		return strBuild.toString();
	}

	/**
	 * Generates an random string (letters and numerics) with specified length
	 * Position of individual characters is random 
	 * @param letterNumericBound
	 * @return
	 * @author Dalibor
	 */
	public static String randomString(int length) 
	{
		if(length<=0)
			throw new RuntimeException("randomString - invalid length: "+length);
		
		Random rand = new Random();
		int numc = rand.nextInt(length);
		return randomString(length-numc,numc);
	}
	
}
