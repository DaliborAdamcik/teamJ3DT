package sk.tsystems.forum.junittest;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.regex.Pattern;

import org.junit.Test;

import sk.tsystems.forum.junittest.TestHelper.CharType;

public class TESTtestHelper {
	private final int numTests = 20000;

	@Test
	public final void testCharacterEnum() {
		for(CharType ct: CharType.values()) {
			switch (ct) {
				case LOWERCASELETTER:
				case NUMERIC:
				case UPPERCASELETTER: break;
				default: fail("Test Not yet implemented"); break;
			}
		}
	}

	@Test
	public final void testRandomCharLOWERCASELETTER() {
		for(int i = 0;i<numTests;i++) 
		{
			char c = TestHelper.RandomChar(CharType.LOWERCASELETTER);
			assertTrue("Invalid character", c>= 'a' && c<='z');
		}
	}
	
	@Test
	public final void testRandomCharUPPERCASELETTER() {
		for(int i = 0;i<numTests;i++) 
		{
			char c = TestHelper.RandomChar(CharType.UPPERCASELETTER);
			assertTrue("Invalid character", c>= 'A' && c<='Z');
		}
	}
	
	@Test
	public final void testRandomCharNUMERIC() {
		for(int i = 0;i<numTests;i++) 
		{
			char c = TestHelper.RandomChar(CharType.NUMERIC);
			assertTrue("Invalid character", c>= '0' && c<='9');
		}
	}

	@Test
	public final void testNumericCount() {
		assertEquals(10, TestHelper.NUMBER_OF_NUMERICS);
	}

	@Test
	public final void testLetterCount() {
		assertEquals(26, TestHelper.NUMBER_OF_LETTERS);
	}
	
	@Test
	public final void testRandomStringIntInt() {
		Random rand = new Random();
		for(int i =0; i<numTests;i++)
		{
			int cntLet = rand.nextInt(250);
			int cntNum = rand.nextInt(250);
			String tested = "";
			try
			{
				tested = TestHelper.randomString(cntLet, cntNum);
			}
			catch(RuntimeException e)
			{
				if(cntLet+cntNum>0)
					throw e;
				
				e.printStackTrace();
			}
			
			
			int contCntNum = 0;
			int contCntLet = 0;
			
			for (int j = 0; j<cntLet+cntNum; j++)
			{
				char c = tested.charAt(j);
				if(c>= 'A' && c<='Z' || c>= 'a' && c<='z')
					contCntLet++;
				else
				if(c>= '0' && c<='9')
					contCntNum++;
				else
					fail("Invalid character"+c);
			}
			
			assertEquals("Invalid string legth", cntLet+cntNum, tested.length());
			assertEquals("Invalid Letters count", cntLet, contCntLet);
			assertEquals("Invalid Numbers count", cntNum, contCntNum);
		}
	}

	@Test
	public final void testRandomStringInt() {
		Pattern pat = Pattern.compile("[A-Za-z0-9]+");
		Random rand = new Random();
		for(int i =0; i<numTests;i++)
		{
			int strLen = rand.nextInt(500)+1;
			String tested = TestHelper.randomString(strLen);
			assertEquals("Invalid string legth", strLen, tested.length());
			assertTrue("Invalid characters found", pat.matcher(tested).matches());
		}
	}

}
